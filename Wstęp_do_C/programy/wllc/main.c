#include<stdio.h>
#include"wllc.h"
#include<stdbool.h>
#include<stdlib.h>


int main()
{
    int n,a;
    wezel *lista1 = init();
    wezel *lista2 = init();
    while(!feof(stdin))
    {
        printf("PODAJ NUMER OPERACJI KTORA CHCESZ WYKONAC:\n");
        // 0 - wypisz liste 1 - dodaj element 2 -dodaj liste 3-polacz listy 4- zmien na jednopoziomowa
        if(scanf("%d",&n)!=1)break;
        if(n==-1)lista2=init();
        if(n==0)
        {
            wypisz(lista1);
            printf("\n");
        }
        if(n==1)
        {
            scanf("%d",&a);
            wezel *s = pushe(lista1,a);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista1 = s;
        }
        if(n==2)
        {
            wezel *s = pushl(lista1,lista2);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista1 = s;
        }
        if(n==3)
        {
            wypisz(MERGE(lista1,lista2));
            printf("\n");

        }
        if(n==4)
        {

        }
        if(n==5)
        {
            wezel *lista3 = init();
            lista3 = kopiuj(lista1);
            wypisz(lista3);
        }
        if(n==10)
        {
            wypisz(lista2);
            printf("\n");
        }
        if(n==11)
        {
            scanf("%d",&a);
            wezel *s = pushe(lista2,a);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista2 = s;

        }
        if(n==12)
        {
            wezel *s = pushl(lista2,lista1);
            if(s==NULL)
            {
                printf("BRAK PAMIECI\n");
                exit(1);
            }
            else lista2 = s;
        }
        if(n==15)
        {
            wezel *lista4 = init();
            lista4 = kopiuj(lista2);
            wypisz(lista4);
        }
    }
    return 0;
}
