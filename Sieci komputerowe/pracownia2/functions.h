//Stanisław Wilczyński 272955
#ifndef FUN
#define FUN

#include "wrapper.h"
#include <unordered_map>
#include <vector>
#include <string>
#include <cstdint>
using namespace std;
const unsigned int UNREACHABLE = 10; 
const int TURN = 3; 
const int TIMEOUT = 3; 
const int PORT = 54321;
const unsigned int UINTMAX = (~0);

struct NetworkData{
	string address, broadcast, network, via;
	unsigned int netmask, distance, timeout;
	bool connected_directly;
	unsigned int original_distance;
	int socket;
};

struct MyPacket{
	u_int8_t bytes[9];
	MyPacket();
	MyPacket(NetworkData& data);
	MyPacket(u_int8_t* new_bytes);
};

unsigned int IpToUInt(string ip);
string UIntToIp(unsigned int ip);
string computeBroadcastAddress(string address, unsigned int netmask);
void initialize();
void readAndSend();
void printRoutingTable();
void updateRoutingTable( NetworkData& network, MyPacket packet, string ip  );
void updateTimeouts();

#endif