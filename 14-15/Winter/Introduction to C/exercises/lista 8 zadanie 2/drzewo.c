//Stanislaw Wilczynski lista 8 zadanie 2 272955 9.12.2014
#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>
#include"drzewo.h"


wezel *init()
{
    return NULL;
}
stos *push(stos *st,wezel *drzewo)
{
    stos *p;
    if((p=(stos *)malloc(sizeof(stos)))==NULL)return NULL;
    p -> nast = st;
    p -> top = drzewo;
    return p;
}
wezel *top(stos *st)
{
    return st -> top;
}
stos *pop(stos *st)
{
    if(st==NULL)return NULL;
    stos *st1;
    st1 = st -> nast;
    return st1;
}
wezel *dodajl(int l)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    p->isn = true;
    p->liczba = l;
    return p;
}
wezel *dodajz(wezel *drzewol,wezel *drzewop, char zn)
{
    wezel *p;
    if((p=(wezel *)malloc(sizeof(wezel)))==NULL)return NULL;
    p->isn = false;
    p->znak = zn;
    p->prawe = drzewop;
    p->lewe = drzewol;
    return p;
}
void wypiszdrzewo(wezel *drzewo)
{
    if(drzewo==NULL)return;
    if(drzewo->isn)
    {
        printf("%d",drzewo -> liczba);
        free(drzewo);
        return;
    }
    else
    {
        if((drzewo -> znak == '*' || drzewo -> znak == '/'))
        {
            bool p = drzewo -> prawe -> isn;
            bool l = drzewo -> lewe -> isn;
            if(!p)putchar('(');
            wypiszdrzewo(drzewo -> prawe);
            if(!p)putchar(')');
            putchar(drzewo -> znak);
            if(!l)putchar('(');
            wypiszdrzewo(drzewo -> lewe);
            if(!l)putchar(')');
        }
        else
        {
            wypiszdrzewo(drzewo -> prawe);
            putchar(drzewo -> znak);
            wypiszdrzewo(drzewo -> lewe);
        }
    }
    free(drzewo);
}
