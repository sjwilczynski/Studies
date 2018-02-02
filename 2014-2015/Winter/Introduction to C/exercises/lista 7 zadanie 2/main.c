// Stanislaw Wilczynski lista 7 zadanie 3 272955 4.12.2014
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include "lista.h"
#include<math.h>

int main()
{
    int n;
    double x;
    wezel *lista1 = init();
    wezel *lista2 = init();
    while(!feof(stdin))
    {
        printf("PODAJ NUMER OPERACJI KTORA CHCESZ WYKONAC:\n"); // 0- wypisz liste 1- dodaj element na poczatek lista, 2- dodaj element na koniec listy, 3 - sprawdz czy lista jest pusta, 4- zwroc wartosc z pocztaku listy, 5 - usun i zwroc wartosc z poczatku listy, 6 - podaj dlugosc listy 7- polacz listy 8 - skopiuj liste
        if(scanf("%d",&n)!=1)break;
        if(n==0)
        {
            wypiszliste(lista1);
        }
        if(n==1)
        {
            scanf("%lf",&x);
            wezel *s = pushp(lista1,x);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista1 = s;
        }
        if(n==2)
        {
            scanf("%lf",&x);
            wezel *s = pushk(lista1,x);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista1 = s;
        }
        if(n==3)
        {
            if(isempty(lista1))printf("LISTA JEST PUSTA\n");
            else printf("LISTA NIE JEST PUSTA\n");
        }
        if(n==4)
        {
            double w;
            w = top(lista1);
            if(isnan(w))printf("LISTA JEST PUSTA\n");
            else printf("%f\n",w);
        }
        if(n==5)
        {
            double w;
            w = pop(lista1);
            if(isnan(w))printf("LISTA JEST PUSTA\n");
            else printf("%f\n",w);

        }
        if(n==6)
        {
            printf("%d\n",dlugosc(lista1));
        }
        if(n==10)
        {
            wypiszliste(lista2);
        }
        if(n==11)
        {
            scanf("%lf",&x);
            wezel *s = pushp(lista2,x);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista2 = s;
        }
        if(n==12)
        {
            scanf("%lf",&x);
            wezel *s = pushk(lista2,x);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista2 = s;
        }
        if(n==13)
        {
            if(isempty(lista2))printf("LISTA JEST PUSTA\n");
            else printf("LISTA NIE JEST PUSTA\n");
        }
        if(n==14)
        {
            double w;
            w = top(lista2);
            if(isnan(w))printf("LISTA JEST PUSTA\n");
            else printf("%f\n",w);
        }
        if(n==15)
        {
            double w;
            w = pop(lista2);
            if(isnan(w))printf("LISTA JEST PUSTA\n");
            else printf("%f\n",w);

        }
        if(n==16)
        {
            printf("%d\n",dlugosc(lista2));
        }
        if(n==7)
        {
            wezel *lista3 = polacz(lista1, lista2);
            wypiszliste(lista3);

        }
        if(n==8)
        {
            lista2 = kopiuj(lista1);
            wypiszliste(lista2);
        }
        if(n==9)
        {
            lista1 = qusort(lista1);
            wypiszliste(lista1);
        }
        /** if(n==-1)
        {
            scanf("%lf",&x);
            lista1 = qusort(lista1);
            lista1 = wstaw(lista1,x);
            wypiszliste(lista1);
        }
         */

    }
    return 0;
}
