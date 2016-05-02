#include<stdio.h>

extern long fib(int n);

int main()
{
	int n;
	scanf("%d",&n);
	printf("%ld\n",fib(n));
	return 0;
}
