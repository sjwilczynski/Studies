//Stanisław Wilczyński 272955
#include "wrapper.h"

ssize_t my_recvfrom(int sockfd, void *buf, size_t len, int flags, struct sockaddr *src_addr, socklen_t *addrlen){
	ssize_t n = recvfrom(sockfd, buf, len, flags, src_addr, addrlen);
	if(n < 0){
		throw std::runtime_error("recvfrom error");
	}
	return n;
}
void my_sendto(int sockfd, const void *buf, size_t len, int flags, const struct sockaddr *dest_addr, socklen_t addrlen){
	int n = sendto(sockfd, buf, len, flags, dest_addr, addrlen);
	if(n < 0){
		throw std::runtime_error("sendto error");
	}
}
void my_setsockopt(int sockfd, int level, int optname, const void *optval, socklen_t optlen){
	int n = setsockopt(sockfd, level, optname, optval, optlen);
	if(n < 0){
		throw std::runtime_error("setsockopt error");
	}
}
void my_gettimeofday(struct timeval *tv, struct timezone *tz){
	int n = gettimeofday( tv, tz );
	if(n < 0){
		throw std::runtime_error("gettimeofday error");
	}
}
int my_select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout){
	int n = select(nfds, readfds, writefds, exceptfds, timeout);
	if(n < 0){
		throw std::runtime_error("select error");
	}
	return n;
}