// Stanislaw Wilczynski lista 6 zadanie 1 272955 20.11.2014
#include<stdio.h>
#include<stdbool.h>
#include "kolejka.h"
#include<math.h>


bool dodaj(double x, struct kolejka *q)
{
    if((q -> pel)==1)
    {
        return false;
    }
    int k = q -> kon;
    //printf("!!!");
    (q -> Q[k])=x;
    //printf("!!!");
    (q -> kon)++;
    (q -> kon) = (q -> kon)%ROZ;
    if ((q -> kon) == (q -> pocz)) (q -> pel)=1;
    return true;
}
double pobierz(struct kolejka *q)
{
    if(pusta(q)) return NAN;
    int p = q -> pocz;
    double c  =  q -> Q[p];
    (q -> pocz)++;
    (q -> pocz) = (q -> pocz)%ROZ;
    if((q -> pel)==1) (q -> pel)=0;
    return c;
}
void wyczysc(struct kolejka *q)
{
    q -> pocz = q -> kon = 0;
}
bool pusta(struct kolejka *q)
{
    if((q -> pocz)==(q -> kon) && (q -> pel)!=1) return true;
    return false;
}

