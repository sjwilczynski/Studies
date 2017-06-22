//Stanisław Wilczyński 272955
#ifndef WRAP
#define WRAP

#include <string.h>
#include <stdexcept>
#include <arpa/inet.h>
#include <netinet/ip_icmp.h>
#include <unistd.h> 
#include <assert.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/socket.h>

void my_setsockopt(int sockfd, int level, int optname, const void *optval, socklen_t optlen);
int my_select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);
int my_socket(int domain, int type, int protocol);
void my_inet_pton(int af, const char *src, void *dst);
void my_bind(int sockfd, struct sockaddr *my_addr, socklen_t addrlen);
int my_accept(int sockfd, struct sockaddr *addr, socklen_t *addrlen);
void my_listen(int sockfd, int backlog);
ssize_t my_recv(int sockfd, void *buf, size_t len, int flags);
ssize_t my_send(int sockfd, const void *buf, size_t len, int flags);

#endif