#include <stdio.h>

extern long insert_sort(long *first, long *last);

int main()
{
	long n = 18;
	long tab[18] = {1467,71,16753,-174364567547,-1,0,1,2,3,6,5,78517,112,91649785295,91864,-123,3285,7186871};
	insert_sort(tab,tab+n-1);
	long i;
	for(i = 0; i < n; i++)
		printf("%ld ",tab[i]);
	return 0;
}
