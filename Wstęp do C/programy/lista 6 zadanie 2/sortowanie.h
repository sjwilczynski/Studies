//Stanislaw Wilczynski lista 6 zadanie 2 272955 22.11.2014
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

struct wynik
{
    double mini;
    double maxi;
    double sred;
};
void wypisz(int t[], int n);
void generujtablice(int t[], int n);
int compare(const void * a, const void * b);
bool sprawdz(int t1[], int t2[], int n);
void testuj(int dane, int rozmiar, struct wynik *wynik1, void (*funkcja1)(void *tab,int dane, int bajty,int (*compare)(const void *, const void *)) );

