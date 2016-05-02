#include <stdio.h>

extern long adds(long x, long y);

int main()
{
	long a,b;
	scanf("%ld %ld",&a,&b);
	long res = adds(a,b);
	printf("%ld\n",res);
	return 0;	
}

