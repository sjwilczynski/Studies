//Stanislaw Wilczynski 272955 Lista 4 zadanie 2 10.11.2014
#include<stdio.h>
#include "funkcjewymierne1.h"

int main()
{
    wymierna x,y;
    printf("WPROWADZ LICZBY KTORYCH SUME CHCESZ POLICZYC:\n");
    x = wczytaj();
    y = wczytaj();
    wypisz(suma(x,y));
    printf("WPROWADZ LICZBY KTORYCH ROZNICE CHCESZ POLICZYC:\n");
    x = wczytaj();
    y = wczytaj();
    wypisz(roznica(x,y));
    printf("WPROWADZ LICZBY KTORYCH ILOCZYN CHCESZ POLICZYC:\n");
    x = wczytaj();
    y = wczytaj();
    wypisz(iloczyn(x,y));
    printf("WPROWADZ LICZBY KTORYCH ILORAZ CHCESZ POLICZYC:\n");
    x = wczytaj();
    y = wczytaj();
    wypisz(iloraz(x,y));
    return 0;
}
