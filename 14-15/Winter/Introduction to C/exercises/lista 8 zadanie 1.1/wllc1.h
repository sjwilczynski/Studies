//Stanislaw Wilczynski lista 8 zadanie 1 272955 5.12 2014
#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>


typedef struct wezel
{
    struct wezel *nast;
    bool isn;
    int liczba;
    struct wezel *podw;
} wezel;

wezel *init();
bool isempty(wezel *lista);
wezel *pushe(wezel *lista,int n);
wezel *pushl(wezel *lista, wezel *podlista);
void wypisz(wezel *lista);
wezel *kopiuj(wezel *lista);
wezel *MERGE(wezel *lista1,wezel *lista2);
wezel *flat(wezel *lista1);


