#include <stdio.h>

long l_max = 0x7FFFFFFFFFFFFFFF;
long l_min = 0x8000000000000000;

#define ADDS(x, y, res) \
	asm("addq %1, %2;"\
		"movq %2, %0;" \
		"movq l_max, %1;" \
		"cmovnsq l_min, %1;" \
		"cmovoq %1, %0" \
		: "=r"(res), "+r"(x),"+r"(y) \
		:  \
		: "cc"   \
		);

int main()
{
	long l1,l2,l3;
	long r1,r2;
	(void)scanf("%ld %ld %ld",&l1,&l2,&l3);
	ADDS(l1,l2,r1);		
	ADDS(l1,l3,r2);
	printf("%ld %ld\n",r1,r2);
	return 0;
}
