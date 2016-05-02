//Stanis³aw Wilczyñski 272955 Lista 4 zadanie 1 7.11.2014
#include<stdio.h>
#include "funkcje.h"

int main()
{
    ZBIOR W,U,X;
    int x;
    printf("podaj zbiory, ktore chcesz zsumowac\n");
    wczytaj(W);wczytaj(U);
    suma(W,U,X);
    printf("suma to:");
    wypisz(X);
    printf("podaj zbiory, ktorych przekroj chcesz policzyc\n");
    wczytaj(W);wczytaj(U);
    przekroj(W,U,X);
    printf("przekroj to:");
    wypisz(X);
    printf("podaj zbior i element, ktory chcesz do niego dodac\n");
    wczytaj(W);scanf("%d",&x);
    dodaj_e(W,x);
    printf("nowy zbior to:");wypisz(W);
    printf("podaj zbior i element ktory chcesz z niego usunac\n");
    wczytaj(W);scanf("%d",&x);
    usun_e(W,x);
    printf("nowy zbior to:");
    wypisz(W);
    return 0;
}


