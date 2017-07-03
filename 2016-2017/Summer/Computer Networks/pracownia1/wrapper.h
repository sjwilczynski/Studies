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

ssize_t my_recvfrom(int sockfd, void *buf, size_t len, int flags, struct sockaddr *src_addr, socklen_t *addrlen);
void my_sendto(int sockfd, const void *buf, size_t len, int flags, const struct sockaddr *dest_addr, socklen_t addrlen);
// jesli sie uda zwraca liczbe wyslanych bajtow wiec mozemy zrobic void
void my_setsockopt(int sockfd, int level, int optname, const void *optval, socklen_t optlen);
//jesli sie uda tylko jedna wartosc -> nic wiecej nie potrzebujemy
void my_gettimeofday(struct timeval *tv, struct timezone *tz);
int my_select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);

#endif