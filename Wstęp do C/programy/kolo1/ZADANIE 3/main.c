#include"liczby.h"
int f(int n)
{
    return 2*n;
}
int main()
{
    lista L = przydziel();
    L->x = 5;
    lista L2 = przydziel();
    L2->x = 3;
    L2->nast = NULL;
    L->nast = L2;
    printf("%d\n",suma(L));
    printf("%d\n",suma(L2));
    L2 = obraz(L,*f);
    printf("%d\n",suma(L2));
    L = maxprefiksow(L);
    printf("%d\n",suma(L));

    return 0;
}
