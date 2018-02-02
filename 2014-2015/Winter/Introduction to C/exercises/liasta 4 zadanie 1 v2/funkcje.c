//Stanis³aw Wilczyñski 272955 Lista 4 zadanie 1 7.11.2014
#include<stdio.h>
#include "funkcje.h"

void wczytaj(ZBIOR z1)
{
    int le,e;
    scanf("%d",&le);
    z1[0]=le;
    for(int i=1;i<=le;i++)
    {
        scanf("%d",&e);
        if(e<=z1[i-1]&&i>1)
        {
            printf("BLEDNE WPISANIE ELEMENTU\n ZACZNIJ OD NOWA\n");
            wyczysc(z1);
            wczytaj(z1);
            return;
        }
        z1[i]=e;
    }
}
void wypisz(ZBIOR z1)
{
    if(z1[0]==0)printf("zbior pusty");
    for(int i=1;i<=z1[0];i++)printf("%d ",z1[i]);
    printf("\n");
}
void wyczysc(ZBIOR z1)
{
    for(int i=0;i<MAX_SET_SIZE;i++)z1[i]=0;
}
void suma(const ZBIOR z1, const ZBIOR z2, ZBIOR wynik)
{
    int le=1;
    int i=1;int j=1;
    while(i<=z1[0] && j<=z2[0])
    {
        if(le == MAX_SET_SIZE)
            {
                    printf("SUMA ZBIORU MA ZBYT WIELE ELEMENTOW. BLAD\n");
                    wyczysc(wynik);
                    return;
            }
        if(z1[i]>z2[j])
        {
                wynik[le]=z2[j];
                j++;
                le++;
        }
        else if(z1[i]<z2[j])
        {
                wynik[le]=z1[i];
                i++;
                le++;
        }
        else
        {
                wynik[le]=z2[j];
                j++;
                i++;
                le++;
        }

    }
    for(int p=i;p<=z1[0];p++)
    {
        if(le == MAX_SET_SIZE)
                {
                    printf("SUMA ZBIORU MA ZBYT WIELE ELEMENTOW. BLAD\n");
                    wyczysc(wynik);
                    return;
                }
        wynik[le]=z1[p];
        le++;
    }
    for(int p=j;p<=z2[0];p++)
    {
        if(le == MAX_SET_SIZE)
                {
                    printf("SUMA ZBIOROW MA ZBYT WIELE ELEMENTOW. BLAD\n");
                    wyczysc(wynik);
                    return;
                }
        wynik[le]=z2[p];
        le++;
    }
    wynik[0]=le-1;
}
void przekroj(const ZBIOR z1, const ZBIOR z2, ZBIOR wynik)
{
    wyczysc(wynik);
    int le=1;
    int i=1;int j=1;
    while(i<=z1[0] && j<=z2[0])
    {
        if(z1[i]>z2[j])
        {
                j++;
        }
        else if(z1[i]<z2[j])
        {
                i++;
        }
        else
        {
                wynik[le]=z2[j];
                j++;
                i++;
                le++;
        }

    }
    wynik[0]=le-1;
}
void dodaj_e(ZBIOR z1, ELEMENT e)
{
    int i=1;
    while(z1[i]<e && i<=z1[0])i++;
    if(z1[i]==e) return;
    ZBIOR w;
    if(z1[0]==MAX_SET_SIZE-1)
    {
        printf("ZBIOR MA ZBYT WIELE ELEMENTOW. BLAD\n");
        return;
    }
    w[0]=z1[0]+1;
    for(int j=1;j<i;j++)w[j]=z1[j];
    w[i]=e;
    for(int j=i+1;j<=w[0];j++)w[j]=z1[j-1];
    int le=w[0];
    for(int j=0;j<=le;j++)z1[j]=w[j];
}
void usun_e(ZBIOR z1, ELEMENT e)
{
    int i=1;
    while(z1[i]<e && i<=z1[0])i++;
    if(i>z1[0])return;
    ZBIOR w;
    w[0]=z1[0]-1;
    for(int j=1;j<i;j++)w[j]=z1[j];
    for(int j=i;j<=w[0];j++)w[j]=z1[j+1];
    int le=w[0];
    for(int j=0;j<=le;j++)z1[j]=w[j];
}


