#include<stdio.h>
#include<stdlib.h>

typedef struct Liczb{
    int x;
    struct Liczb *nast;
} liczba;
typedef liczba* lista;

lista init();
lista przydziel();
int suma(lista lista1);
lista obraz(lista lista1, int (*f)(int n));
lista maxprefiksow(lista lista1);
