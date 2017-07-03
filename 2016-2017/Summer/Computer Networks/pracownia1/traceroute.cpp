//Stanisław Wilczyński 272955
#include "functions.h"

int main(int argc, char **argv){

	int sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP);
	if (sockfd < 0) {
		printf("Socket error\n");
		return EXIT_FAILURE;
	}
	if(argc != 2){
		printf("Wrong number of arguments\n");
		return EXIT_FAILURE;
	}
	struct sockaddr_in recipient;
	bzero (&recipient, sizeof(recipient));
	recipient.sin_family = AF_INET;
	if( inet_pton(AF_INET, argv[1], &recipient.sin_addr) == 0){
		printf("Wrong IP\n");
		return EXIT_FAILURE;
	}
	
	fd_set descriptors;
	std::string ip_string[3];
	int time_delta[3];

	for(int ttl = 1; ttl <= 30; ttl++){
		
		FD_ZERO (&descriptors);
		FD_SET (sockfd, &descriptors);

		printf("%d.  ",ttl);
		struct icmphdr icmp_headers[3];
		struct timeval time_send[3];
		for(int i = 0; i < 3; i++){
			ip_string[i] = "";
			time_delta[i] = 0;
		}

		for(int i = 0; i < 3; i++){
			my_gettimeofday(&time_send[i], NULL);
			generate_and_send_icmp_header(icmp_headers[i], ttl, 3*ttl-i, &recipient, sockfd); //taki sequence zapewnia ignorowanie starych pakietow
		} 

		struct timeval time_limit; 
		time_limit.tv_sec = 1; 
		time_limit.tv_usec = 0;
		int ready = my_select (sockfd+1, &descriptors, NULL, NULL, &time_limit);

		while(ready){

			struct sockaddr_in sender;
			socklen_t sender_len = sizeof(sender);
			u_int8_t buffer[IP_MAXPACKET+1];
			ssize_t packet_len = my_recvfrom (sockfd, buffer, IP_MAXPACKET, 0, (struct sockaddr*) &sender, &sender_len);
			char ip_str[20]; 
			if( inet_ntop (AF_INET, &(sender.sin_addr), ip_str, sizeof(ip_str)) == 0){
				continue;
			}
			struct iphdr* ip_header = (struct iphdr*) buffer;
			u_int8_t* icmp_packet = buffer + 4 * ip_header->ihl;
			packet_len -= 4 * ip_header->ihl;
			if(packet_len < 0){
				continue;
			}
			struct icmphdr* icmp_header_recv = (struct icmphdr*) icmp_packet;

			if( (icmp_header_recv->type ==  ICMP_TIME_EXCEEDED) && (icmp_header_recv->code == ICMP_EXC_TTL) ){
				struct iphdr* ip_header_old = (struct iphdr*) (buffer + 4 * ip_header->ihl + 8);
				packet_len -= 8;
				u_int8_t* icmp_packet_old = buffer + 4 * ip_header->ihl + 8 + 4*ip_header_old->ihl;
				packet_len -= 4*ip_header_old->ihl;
				icmp_header_recv = (struct icmphdr*) icmp_packet_old;
				//bo najpierw jest zwykly naglowek, potem icmp 8-bajtowy, potem zaczyna sie naglowek wyslanego z komputera
			}

			for(int i = 0; i < 3; i++){
				if( check_icmp_package(&icmp_headers[i], icmp_header_recv) ){
					ip_string[i] = std::string(ip_str);
					time_delta[i] = time_dif(&time_send[i]);
				}
			}
			if( ip_string[0] != "" && ip_string[1] != "" && ip_string[2] != ""){
				break;
			}
			ready = my_select (sockfd+1, &descriptors, NULL, NULL, &time_limit);
		}

		std::string result = generate_response(ip_string, time_delta) ;
		printf("%s\n",result.c_str());
		if( result.find(argv[1]) != std::string::npos ){
			break;
		}
		
	}
	return EXIT_SUCCESS;
}