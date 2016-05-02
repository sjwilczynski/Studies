// Stanislaw Wilczynski lista 6 zadanie 1 272955 20.11.2014
#include<stdio.h>
#include<stdbool.h>

#define ROZ 10000

struct kolejka
{
    double Q[ROZ];
    int pocz;
    int kon;
    int pel;
};

bool dodaj(double x, struct kolejka *q);
double pobierz(struct kolejka *q);
void wyczysc(struct kolejka *q);
bool pusta(struct kolejka *q);
