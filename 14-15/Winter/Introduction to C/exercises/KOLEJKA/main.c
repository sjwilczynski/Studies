// Stanislaw Wilczynski lista 6 zadanie 1 272955 20.11.2014
#include<stdio.h>
#include<stdbool.h>
#include<math.h>
#include "kolejka.h"

int main()
{
    struct kolejka KOLEJ;
    int n;
    double v;
    struct kolejka *w;
    w = &KOLEJ;
    wyczysc(w);
    while(!feof(stdin))
    {
        printf("PODAJ JAKA OPERACJE CHCESZ WYKONAC\n"); // 1 - wyczysc, 2 - sprawdz czy pusta, 3 - dodaj, 4 -pobierz
        if(scanf("%d",&n)!=1)break;
        if(n==1)
        {
            wyczysc(w);
            printf("KOLEJKA ZOSTALA WYCZYSZCZONA\n");
        }
        if(n==2)
        {
            if(pusta(w))printf("KOLEJKA JEST PUSTA\n");
            else printf("KOLEJKA NIE JEST PUSTA\n");
        }
        if(n==3)
        {
            printf("PODAJ ELEMENT\n");
            scanf("%lf",&v);
            if(!dodaj(v,w)) printf("KOLEJKA JEST PELNA\n");
            else printf("ELEMENT ZOSTAL DODANY\n");
        }
        if(n==4)
        {
            double c = pobierz(w);
            if(isnan(c))printf("KOLEJKA JEST PUSTA\n");
            else printf("%f\n",c);
        }
    }
    return 0;
}
