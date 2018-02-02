//Stanislaw Wilczynski 272955 Lista 4 zadanie 2 10.11.2014
#include<stdio.h>
#include "funkcjewymierne1.h"
int p=1;
lld nwd(lld a, lld b)
{
    lld c;
    lld a1=a;
    lld b1=b;
    if(a1<0)a1=-a1;
    if(b1<0)b1=-b1;
    while(b1>0)
    {
        c=a1%b1;
        a1=b1;
        b1=c;
    }
    return a1;
}
lld nww(lld a,lld b)
{
    lld a1=a;
    lld b1=b;
    if(a1<0)a1=-a1;
    if(b1<0)b1=-b1;
    lld c = a1*b1/nwd(a1,b1);
    return c;
}

lld licznik(wymierna x)
{
    return (x >> BITY);
}
lld mianownik(wymierna x)
{
    return ((x << BITY) >> BITY);
}
wymierna tworz(lld lx,lld mx)
{
    wymierna w;
    lld n = nwd(lx,mx);
    lx=lx/n;
    mx=mx/n;
    w = (lx << BITY) + mx;
    return w;
}
wymierna wczytaj()
{
    p=1;
    lld lx,mx=0;
    scanf("%I64d",&lx);
    getchar();
    while(mx==0)
    {
        scanf("%I64d",&mx);
        if(mx==0) printf("BLAD. SPROBUJ JESZCzE RAZ\n");
    }
    lld n = nwd(lx,mx);
    lx=lx/n;
    mx=mx/n;
    return tworz(lx,mx);
}
void wypisz(wymierna x)
{
    if(p==0)return;
    printf("%lld/%lld\n",licznik(x),mianownik(x));
}
wymierna suma(wymierna x, wymierna y)
{
    lld nmian = nww(mianownik(x),mianownik(y));
    lld nlicz = licznik(x)*(nmian/mianownik(x)) + licznik(y)*(nmian/mianownik(y));
    return tworz(nlicz,nmian);
}
wymierna roznica(wymierna x,wymierna y)
{
    lld nmian = nww(mianownik(x),mianownik(y));
    lld nlicz = licznik(x)*(nmian/mianownik(x)) - licznik(y)*(nmian/mianownik(y));
    return tworz(nlicz,nmian);
}
wymierna iloczyn(wymierna x,wymierna y)
{
    lld nmian = mianownik(x) * mianownik(y);
    lld nlicz = licznik(x) * licznik(y);
    return tworz(nlicz,nmian);
}
wymierna iloraz(wymierna x,wymierna y)
{
    if(licznik(y)==0)
    {
        printf("BLAD. NIE MOZNA DZIELIC PRZEZ 0\n");
        p=0;
        return 1;
    }
    lld nmian = mianownik(x) * licznik(y);
    lld nlicz = licznik(x) * mianownik(y);
    if (nmian<0)
    {
        nmian = -nmian;
        nlicz = -nlicz;
    }
    return tworz(nlicz,nmian);
}

