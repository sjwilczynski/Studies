#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

typedef struct para{
    double x;
    int y;
}para;

typedef struct rzadka{
    int wiersze[10000]; //pierwszy element po ostatnim niepustym wierszu to -1
    para liczby[10000][1000]; // pierwszy element pary - wartosc, drugi kolumna i tez po ostatnim elemencie -1
}rzadka;


double wartosc(rzadka *T,int i,int j);
rzadka *dodaj_e(rzadka *T,double z,int i,int j);
rzadka *trasponuj(rzadka *T);


