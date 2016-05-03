#include<stdio.h>
#include"wllc.h"
#include<stdbool.h>
#include<stdlib.h>

wezel *init()
{
    return NULL;
}
bool isempty(wezel *lista)
{
    return(lista == NULL);
}
wezel *pushe(wezel *lista,int n)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    p -> nast = lista;
    p -> isn = true;
    p -> liczba = n;
    return p;
}
wezel *pushl(wezel *lista, wezel *podlista)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    p -> nast = lista;
    p -> isn = false;
    p -> podw = podlista;
    return p;
}
void wypisz(wezel *lista)
{
    if(isempty(lista))
    {
        printf("[]");
        return;
    }
    wezel *p = lista;
    putchar('[');
    while(p!=NULL)
    {
        if(p ->isn)
        {
            printf("%d",p->liczba);
            if(p->nast!=NULL)printf(",");
        }
        else
        {
            wypisz(p->podw);
            if(p->nast!=NULL)printf(",");
        }
        p = p->nast;
    }
    printf("]");
}
wezel *kopiuj(wezel *lista)
{
    if(isempty(lista))return NULL;
    wezel *new = kopiuj(lista ->nast);
    wezel *p = lista;
    if(p->isn) new = pushe(new,p->liczba);
    else new = pushl(new,kopiuj(p->podw));
    return new;
}
wezel *MERGE(wezel *lista1,wezel *lista2)
{
    wezel *lista3 = kopiuj(lista1);
    wezel *lista4 = kopiuj(lista2);
    wezel *p = lista3;
    while(p->nast!=NULL)p = p->nast;
    p->nast = lista4;
    return lista3;
}
wezel *flat(wezel *lista1)
{
    wezel *new = init();
    wezel *p = lista1;
    while(p!=NULL)
    {
        if(p->isn)
        p = p->nast;
    }

}

