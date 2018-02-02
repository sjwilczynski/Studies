//Stanislaw Wilczynski lista 6 zadanie 2 272955 22.11.2014
 #include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "sortowanie.h"
int compare(const void * a, const void * b)
{
    return ( *(int*)a - *(int*)b );
}
void wypisz(int t[],int n)
{
    for(int i=0; i<n; i++)printf("%d ",t[i]);
    printf("\n\n");
}
void generujtablice(int t[], int n)
{
    for(int i=0; i<n; i++) t[i]=rand();
}
bool sprawdz(int t1[], int t2[], int n) //t1 - przed sortowaniem t2 -po sortowaniu
{
    for(int i=0; i<n; i++)
    {
        int p=0;
        for(int j=0; j<n; j++)
        {
            if(t1[i]==t2[j])
            {
                p=1;
                break;
            }
        }
        if(p==0)
        {
                printf("!!%d",i);
                return false;
        }

    }
    for(int i=1; i<=n; i++)
    {
        if(t2[i]<t2[i-1])
        {
            printf("%d",i);
            return false;
        }
    }
    return true;

}
void testuj(int dane, int rozmiar, struct wynik *wynik1, void (*funkcja1)(void *tab,int dane, int bajty,int (*compare)(const void *, const void *)) )
{
    wynik1 -> maxi = 0;
    wynik1 -> mini = 99999999;
    wynik1 -> sred = 0;

    int pocz,kon;
    int tab[rozmiar];
    for(int i=1; i<=dane; i++)
    {

        generujtablice(tab,rozmiar);
        pocz = clock();
        (*funkcja1)(tab, rozmiar, sizeof(int), compare);
        kon = clock();
        int czas = kon - pocz;
        if(czas > wynik1 -> maxi)wynik1 -> maxi = czas;
        if(czas < wynik1 -> mini)wynik1 -> mini = czas;
        wynik1 -> sred = (wynik1 -> sred *(i-1) + czas)/i;
    }

}
