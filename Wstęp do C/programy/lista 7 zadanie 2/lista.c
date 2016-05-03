// Stanislaw Wilczynski lista 7 zadanie 3 272955 4.12.2014
// sortowania przez wstawianie nie zaprogramowalem
#include<stdlib.h>
#include<stdbool.h>
#include<stdio.h>
#include "lista.h"
#include<math.h>

wezel *init()
{
    return NULL;
}
wezel *pushp(wezel *lista, double x)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    else if(!isempty(lista))
    {
        p -> nast = lista -> nast;
        lista -> nast = p;
    }
    else
    {
        p -> nast = p;
        lista = p;
    }
    p -> info = x;
    return lista;
}
wezel *pushk(wezel *lista, double x)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    else if(!isempty(lista))
    {
       p -> nast = lista -> nast;
       lista -> nast = p;
    }
    else
    {
        p -> nast = p;
    }
    p -> info = x;
    return p;
}
double top(wezel *lista)
{
    return (lista==NULL ? NAN:lista -> nast -> info);

}
double pop(wezel *lista)
{
    double wynik;
    wezel *p;
    if(lista==NULL)return NAN;
    else if(lista -> nast != lista)
    {
        p = lista -> nast;
        wynik = p -> info;
        lista -> nast = p -> nast;
        free(p);
    }
    else
    {
        wynik = lista -> info;
        lista = NULL;
    }
    return wynik;
}
bool isempty(wezel *lista)
{
    return(lista==NULL);
}
int dlugosc(wezel *lista)
{
    int wynik=1;
    if(isempty(lista))return 0;
    for(wezel *p = lista; p-> nast != lista; p  = p-> nast)wynik++;
    return wynik;
}
void wypiszliste(wezel *lista)
{
    if(isempty(lista))
    {
        printf("LISTA JEST PUSTA\n");
        return;
    }
    wezel *p = lista -> nast;
    for(int i=1;i<=dlugosc(lista);i++)
    {
        printf("%f ", p -> info);
        p = p -> nast;
    }
    printf("\n");
}
wezel *polacz(wezel *lista1, wezel *lista2)
{
    if(isempty(lista1))return lista2;
    if(isempty(lista2))return lista1;
    wezel *lista3 = lista1;
    wezel *lista4 = lista2;
    wezel *p = lista3 -> nast;
    lista3 -> nast = lista4 -> nast;
    lista4 -> nast = p;
    return lista4;
}
wezel *kopiuj(wezel *lista1)
{
    wezel *lista2 = init();

    if(!isempty(lista1))
    {
        wezel *p = lista1->nast;
        while(p->nast!=lista1->nast)
        {
            pushp(lista2,p->info);
            p=p->nast;
        }
        lista2 = p;
    }
    return lista2;
}
wezel *qusort(wezel *lista)
{
    if(!isempty(lista) && lista->nast != lista)
    {
        wezel *lista1 = init();
        wezel *lista2 = init();
        double piwot = lista->nast->info;
        double tmp;
        wezel *p = lista -> nast -> nast;
        while(p!=lista->nast)
        {
            tmp = p->info;
            if(tmp > piwot) lista2 = pushk(lista2,tmp);
            else lista1 = pushk(lista1,tmp);
            p = p->nast;
        }
        lista1 = qusort(lista1);
        lista2 = qusort(lista2);
        lista1 = pushk(lista1,piwot);
        lista = polacz(lista1,lista2);
    }
    return lista;
}
/**
    wezel *insersort(wezel *lista)
{

}
wezel *wstaw(wezel *lista,double x)
{
    wezel *p;
    int t=0;
    wezel *q = lista->nast;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    else if(!isempty(lista))
    {
        do
        {
            if(x>q->nast->info)q=q->nast;
            else
            {
                wezel *r = q->nast;
                q->nast = p;
                p->nast = r;
                t=1;
                p->info = x;
            }
            if(t==1)q=lista->nast;
        }while(q!=lista->nast);
        if(q==lista->nast && t==0)
        {
            wezel *r = q->nast;
            q->nast = p;
            p->nast = r;
            p->info = x;
        }
    }
    else lista = pushp(lista,x);
    return lista;
}
 */

