//Stanisław Wilczyński 272955
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <errno.h>
#include <unistd.h>
#include <algorithm>
#include <cstdio>
#include "functions.h"

using namespace std;

vector<NetworkData> networks;
unordered_map<string, NetworkData> RoutingTable;
unordered_map<int, string> SocketToAddress;
fd_set master_read;
fd_set master_write;    
fd_set read_fds;
fd_set write_fds;  

void initialize(){
	int n;
	scanf("%d",&n);
	char buffer[20];
	for(int i = 0; i < n; i++){

		NetworkData newNetwork;
		scanf("%s distance %u", buffer, &newNetwork.distance);
		unsigned short a,b,c,d,e;
		sscanf(buffer, "%hu.%hu.%hu.%hu/%hu", &a, &b, &c, &d, &e);
		newNetwork.netmask = e;
		newNetwork.address = to_string(a) + "." + to_string(b) + "." + to_string(c) + "." + to_string(d);
		newNetwork.via = "";
		newNetwork.broadcast = computeBroadcastAddress(newNetwork.address, newNetwork.netmask);
		newNetwork.connected_directly = true;
		newNetwork.original_distance = newNetwork.distance;
		newNetwork.timeout = TIMEOUT;
		networks.push_back(newNetwork);
	}

	for(auto& network : networks){

		network.socket = my_socket(AF_INET, SOCK_DGRAM, 0);
		network.timeout = TIMEOUT;
		int broadcast = 1;
		my_setsockopt(network.socket, SOL_SOCKET, SO_BROADCAST, &broadcast, sizeof(broadcast));
		sockaddr_in addrInfo;
		addrInfo.sin_family = AF_INET;
		addrInfo.sin_port = htons(PORT);
		my_inet_pton(AF_INET, network.broadcast.c_str(), &addrInfo.sin_addr.s_addr);
		my_bind(network.socket, (sockaddr*) &addrInfo, sizeof(addrInfo));
		RoutingTable[network.broadcast] = network;
		SocketToAddress[network.socket] = network.broadcast;
	}
}

void readAndSend(){

    int fdmax = 0;
    FD_ZERO(&master_read); 
    FD_ZERO(&master_write);   
    FD_ZERO(&read_fds);
    FD_ZERO(&write_fds);

	for(auto& network:networks){
		FD_SET(network.socket, &master_read);
		FD_SET(network.socket, &master_write);
		if(network.socket > fdmax){
			fdmax = network.socket;
		}
	}
	read_fds = master_read;
	write_fds = master_write;

	struct timeval time_limit; 
	time_limit.tv_sec = TURN; 
	time_limit.tv_usec = 0;

	int ready = my_select (fdmax+1, &read_fds, &write_fds, NULL, &time_limit);

	while(ready){

		for(int i = 0; i <= fdmax; i++){
			if(FD_ISSET(i, &read_fds)){
				
				struct sockaddr_in sender;
				socklen_t sender_len = sizeof(sender);
				u_int8_t buffer[9];
				ssize_t packet_len = my_recvfrom (i, buffer, 9, 0, (struct sockaddr*) &sender, &sender_len);
				char ip_str[20]; 
				inet_ntop (AF_INET, &(sender.sin_addr), ip_str, sizeof(ip_str));
				
				auto it = RoutingTable.find(SocketToAddress[i]);
				if(it != RoutingTable.end()){
					NetworkData& network = it->second;
					string ip = ip_str;
					if(ip != network.address && packet_len == 9){
						MyPacket packet(buffer);
						updateRoutingTable(network, packet, ip);
					}
				}
				//FD_CLR(i, &master_read); 
			}
			
			if(FD_ISSET(i, &write_fds)){
				auto it = RoutingTable.find(SocketToAddress[i]);
				if(it != RoutingTable.end()){
					NetworkData& network = it->second;
					sockaddr_in destination;
					destination.sin_family = AF_INET;
					destination.sin_port = htons(PORT);
					my_inet_pton(AF_INET, network.broadcast.c_str(), &destination.sin_addr.s_addr);

					for(auto& pair : RoutingTable){
						NetworkData newNetwork = pair.second;
						if( !(newNetwork.distance == UNREACHABLE && newNetwork.timeout == 0)){
							MyPacket packet(newNetwork);
							if(sendto(network.socket, packet.bytes, 9, 0, (sockaddr*) &destination, sizeof(destination)) < 0){
								if(network.distance != UNREACHABLE){
									network.distance = UNREACHABLE;
									network.timeout = TIMEOUT;
								}
							} else{
								network.timeout = TIMEOUT;
								if(network.original_distance < network.distance){
									network.distance = network.original_distance;
									network.via = "";
								}
							}
						}
					}
					FD_CLR(i, &master_write); 
				}
			}
			
		}
		read_fds = master_read;
		write_fds = master_write;
		ready = my_select (fdmax+1, &read_fds, &write_fds, NULL, &time_limit);
	}
	sleep(TURN);
}

void updateRoutingTable( NetworkData& senderNetwork, MyPacket packet, string ip ){
	
	u_int32_t address;
	u_int32_t distance;
	memcpy(&address, packet.bytes, 4);
	unsigned int netmask = packet.bytes[4];
	memcpy(&distance, packet.bytes+5, 4);
	distance = ntohl(distance);
	distance = distance == UINTMAX ? UNREACHABLE : distance;
	string newAddress = UIntToIp(ntohl(address));
	auto it = RoutingTable.find(newAddress);
	if(it != RoutingTable.end()){
		auto& oldNetwork = it->second;

		if( oldNetwork.via == ip || distance + senderNetwork.distance < oldNetwork.distance){
			oldNetwork.distance = min( distance + senderNetwork.distance, UNREACHABLE);
			oldNetwork.via = oldNetwork.distance < UNREACHABLE ? ip : "";
			if(oldNetwork.distance < UNREACHABLE){
				oldNetwork.timeout = TIMEOUT;
			}
		}
	}
	else if(distance + senderNetwork.distance < UNREACHABLE){
		NetworkData newNetwork;
		newNetwork.broadcast = newAddress;
		newNetwork.netmask = netmask;
		newNetwork.timeout = TIMEOUT;
		newNetwork.via = ip;
		newNetwork.connected_directly = false;
		newNetwork.distance = distance + senderNetwork.distance;
		RoutingTable[newAddress] = newNetwork; 
	}
	RoutingTable[senderNetwork.broadcast].timeout = TIMEOUT;
}

void updateTimeouts(){

	auto it = RoutingTable.begin();
	while(it != RoutingTable.end()){
		NetworkData& network = it->second;
		if(network.timeout == 0){
			if(network.distance == UNREACHABLE && !network.connected_directly){
				it = RoutingTable.erase(it);
			}
			else{
				network.distance = UNREACHABLE;
				if(!network.connected_directly){
					network.timeout = TIMEOUT;
				}
				network.via = "";
				it++;
			}
		}
		else{
			it++;
		}
	}
	for(auto& pair : RoutingTable){
		if(pair.second.timeout > 0){
			pair.second.timeout--;
		}
	}
}

void printRoutingTable(){
	for(auto& pair : RoutingTable){
		NetworkData& network = pair.second;
		printf("%s/%u ", network.broadcast.c_str(), network.netmask);
	
		if(network.distance < UNREACHABLE){
			printf("distance %d ", network.distance);
			if(network.connected_directly && network.via == ""){
				printf("connected directly");
			}
			else{
				printf("via %s", network.via.c_str());
			}
		}
		else{
			printf("unreachable");
			if(network.connected_directly){
				printf(" connected directly");
			}
		}
		printf(" timeout %u", network.timeout);
		printf("\n");
		
	}
	printf("------------------------------------------\n");
}

MyPacket::MyPacket(){}
MyPacket::MyPacket(NetworkData& data){
	unsigned short a,b,c,d;
	u_int32_t distance;
	sscanf(data.broadcast.c_str(), "%hu.%hu.%hu.%hu", &a, &b, &c, &d);
	bytes[0] = a;
	bytes[1] = b;
	bytes[2] = c;
	bytes[3] = d;
	bytes[4] = data.netmask;
	distance = data.distance == UNREACHABLE ? UINTMAX : data.distance;
	distance = htonl(data.distance);
	memcpy(bytes+5, &distance, sizeof(distance));
}
MyPacket::MyPacket(u_int8_t* new_bytes){
	memcpy(bytes, new_bytes, 9);
}

unsigned int IpToUInt(string ip){
	unsigned short a,b,c,d;
	sscanf(ip.c_str(), "%hu.%hu.%hu.%hu", &a, &b, &c, &d);
	unsigned int addr = (unsigned int) d;
	addr |= ((unsigned int) c) << 8;
	addr |= ((unsigned int) b) << 16;
	addr |= ((unsigned int) a) << 24;
	return addr;
}

string UIntToIp(unsigned int ip){
	unsigned int a,b,c,d;
	a = ip >> 24;
	b = (ip << 8) >> 24;
	c = (ip << 16) >> 24;
	d = (ip << 24) >> 24;
	return to_string(a) + "." + to_string(b) + "." + to_string(c) + "." + to_string(d);
}

string computeBroadcastAddress(string address, unsigned int netmask){
	unsigned int addr = IpToUInt(address);
	unsigned int mask = ((unsigned int) -1) << (32-netmask);
	return UIntToIp(addr | ~mask);
}

