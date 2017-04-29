//Stanisław Wilczyński 272955
#include "functions.h"

u_int16_t compute_icmp_checksum (const u_int16_t *buff, int length){
	u_int32_t sum;
	const u_int16_t* ptr = buff;
	assert (length % 2 == 0);
	for (sum = 0; length > 0; length -= 2)
		sum += *ptr++;
	sum = (sum >> 16) + (sum & 0xffff);
	return (u_int16_t)(~(sum + (sum >> 16)));
}

bool check_icmp_package( struct icmphdr* icmp_header, struct icmphdr* icmp_header_recv ){
	bool b1 = (icmp_header_recv->type == ICMP_ECHOREPLY || icmp_header_recv->type == ICMP_ECHO );
	bool b2 = (icmp_header_recv->un.echo.id == icmp_header->un.echo.id);
	bool b3 = (icmp_header_recv->un.echo.sequence == icmp_header->un.echo.sequence);
	return b1 && b2 && b3;
}

void generate_and_send_icmp_header( struct icmphdr &icmp_header, int ttl, int sequence, sockaddr_in* address, int sockfd ){
	icmp_header.type = ICMP_ECHO;
	icmp_header.code = 0;
	icmp_header.un.echo.id = getpid();
	icmp_header.un.echo.sequence = sequence;
	icmp_header.checksum = 0;
	icmp_header.checksum = compute_icmp_checksum ( ( u_int16_t* ) &icmp_header, sizeof( icmp_header ) );
	my_setsockopt ( sockfd, IPPROTO_IP, IP_TTL, &ttl, sizeof(int));
	my_sendto ( sockfd, &icmp_header, sizeof(icmp_header), 0, (struct sockaddr*) address, sizeof(*address) );
}


int time_dif(struct timeval *time_send){
	
	struct timeval time_recv;
	my_gettimeofday( &time_recv, NULL);
	if ((time_recv.tv_usec -= time_send->tv_usec) < 0)   {
		time_recv.tv_sec--;
		time_recv.tv_usec += 1000000;
	}
	time_recv.tv_sec -= time_send->tv_sec;
	return (time_recv.tv_sec*1000 + time_recv.tv_usec/1000);
}


std::string generate_response(std::string* ip_string, int* time_delta){
	if( ip_string[0] == "" && ip_string[1] == "" && ip_string[2] == ""){
		return "*";
	}

	std::string s = ip_string[0];
	if( ip_string[0] != ip_string[1] ){
		s += " ";
		s += ip_string[1];
	}
	if( ip_string[2] != ip_string[0] && ip_string[2] != ip_string[1] ){
		s += " ";
		s += ip_string[2];
	}

	std::string time = "";
	if( ip_string[0] == "" || ip_string[1] == "" || ip_string[2] == "" ){
		time = "???";
	} else{
		time = std::to_string( (time_delta[0] + time_delta[1] + time_delta[2])/3 ) + "ms" ;
	}
	return s + "   " + time;
}
