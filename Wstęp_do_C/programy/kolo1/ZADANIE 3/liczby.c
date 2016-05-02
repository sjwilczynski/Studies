#include"liczby.h"


lista init()
{
    return NULL;
}
lista przydziel()
{
    lista wsk;
    if((wsk=(lista)malloc(sizeof(liczba)))==NULL)return NULL;
    return wsk;
}
int suma(lista lista1)
{
    lista wsk = lista1;
    int sum = 0;
    while(wsk!=NULL)
    {
        sum = sum+wsk->x;
        wsk = wsk->nast;
    }
    return sum;
}
lista obraz(lista lista1, int (*f)(int n))
{
    lista wsk = lista1;
    while(wsk!=NULL)
    {
        wsk->x  = f(wsk->x);
        wsk = wsk->nast;
    }
    return lista1;
}
lista maxprefiksow(lista lista1)
{
    lista wsk = lista1;
    int maks = -100000000;
    lista pierwszy = NULL;
    lista ostatni = NULL;
    while(wsk!=NULL)
    {
        if(wsk->x>maks)
        {
            lista nwsk = przydziel();
            if(pierwszy==NULL)pierwszy = nwsk;
            nwsk->x = wsk-> x;
            maks = wsk->x;
            nwsk->nast = NULL;
            if(ostatni == NULL) ostatni = nwsk;
            else
            {
                ostatni->nast = nwsk;
                ostatni = nwsk;
            }
        }
        wsk = wsk->nast;
    }
    return pierwszy;
}
