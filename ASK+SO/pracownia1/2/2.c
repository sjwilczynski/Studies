#include <stdio.h>

extern int clz(long a);

int main()
{
	long a;
	scanf("%ld",&a);
	printf("%d\n",clz(a));	
	return 0;
}
