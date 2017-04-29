//Stanisław Wilczyński 272955
#ifndef FUN
#define FUN

#include "wrapper.h"

u_int16_t compute_icmp_checksum (const void *buff, int length);
bool check_icmp_package( struct icmphdr* icmp_header, struct icmphdr* icmp_header_recv);
void generate_and_send_icmp_header( struct icmphdr &icmp_header, int ttl, int sequence, sockaddr_in* address, int sockfd );
int time_dif(struct timeval *tp);
std::string generate_response(std::string* ip_string, int* time_delta);

#endif