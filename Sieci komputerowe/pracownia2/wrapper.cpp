//Stanisław Wilczyński 272955
#include "wrapper.h"

ssize_t my_recvfrom(int sockfd, void *buf, size_t len, int flags, struct sockaddr *src_addr, socklen_t *addrlen){
	ssize_t n = recvfrom(sockfd, buf, len, flags, src_addr, addrlen);
	if(n < 0){
		throw std::runtime_error("recvfrom error");
	}
	return n;
}
void my_setsockopt(int sockfd, int level, int optname, const void *optval, socklen_t optlen){
	int n = setsockopt(sockfd, level, optname, optval, optlen);
	if(n < 0){
		throw std::runtime_error("setsockopt error");
	}
}
int my_select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout){
	int n = select(nfds, readfds, writefds, exceptfds, timeout);
	if(n < 0){
		throw std::runtime_error("select error");
	}
	return n;
}

int my_socket(int domain, int type, int protocol){
	int n = socket(domain, type, protocol);
	if(n < 0){
		throw std::runtime_error("socket error");
	}	
	return n;
}

void my_inet_pton(int af, const char *src, void *dst){
	int n = inet_pton(af, src, dst);
	if(n < 0){
		throw std::runtime_error("inet_pton error");
	}	
}

void my_bind(int sockfd, struct sockaddr *my_addr, socklen_t addrlen){
	int n = bind(sockfd, my_addr, addrlen);
	if(n < 0){
		perror("Bind problem: ");
		throw std::runtime_error("bind error");
	}	
}