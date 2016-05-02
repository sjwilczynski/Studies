//Stanislaw Wilczynski lista 8 zadanie 2 272955 9.12.2014
#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>


typedef struct wezel{
    bool isn;
    struct wezel *lewe;
    struct wezel *prawe;
    int liczba;
    char znak;
}wezel;
typedef struct stos{
    struct stos *nast;
    wezel *top;
}stos;

wezel *init();
stos *push(stos *st,wezel *drzewo);
wezel *top(stos *st);
stos *pop(stos *st);
wezel *dodajl(int l);
wezel *dodajz(wezel *drzewol,wezel *drzewop, char zn);
void wypiszdrzewo(wezel *drzewo);


