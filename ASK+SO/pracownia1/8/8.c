#include<stdio.h>

extern double approx_sqrt(double x, double epsilon);

int main()
{
	double x;
	scanf("%lf",&x);
	printf("%lf\n",approx_sqrt(x,0.00001));
	return 0;
}
