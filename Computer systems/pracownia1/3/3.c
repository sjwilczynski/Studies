#include <stdio.h>

typedef struct
{
	unsigned long lcm;
	unsigned long gcd;
}result_t;

extern result_t lcm_gcd(unsigned long a, unsigned long b);


int main()
{	
	unsigned long a;
	unsigned long b;	
	scanf("%lu %lu",&a,&b);
	result_t res = lcm_gcd(a,b);
	printf("%lu %lu\n",res.lcm,res.gcd);
	return 0;
}
