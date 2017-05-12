#include <stdio.h>

extern unsigned mulf(unsigned a, unsigned b);

int main()
{
	unsigned x,y;
	scanf("%x %x",&x,&y);
	printf("%#x\n", mulf(x,y));
	return 0;
}
