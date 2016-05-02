// Stanislaw Wilczynski lista 7 zadanie 3 272955 4.12.2014
#include<stdlib.h>
#include<stdbool.h>

typedef struct wezel
{
    double info;
    struct wezel *nast;
} wezel;


wezel *init();
wezel *pushp(wezel *lista, double x);
wezel *pushk(wezel *lista, double x);
double top(wezel *lista);
double pop(wezel *lista);
bool isempty(wezel *lista);
int dlugosc(wezel *lista);
void wypiszliste(wezel *lista);
wezel *polacz(wezel *lista1, wezel *lista2);
wezel *kopiuj(wezel *lista1);
wezel *quicksort(wezel *lista);
wezel *insort(wezel *lista);
wezel *wstaw(wezel *lista, double x);
wezel *qusort(wezel *lista);
//wezel *insersort(wezel *lista);
//wezel *wstaw(wezel *lista,double x);


