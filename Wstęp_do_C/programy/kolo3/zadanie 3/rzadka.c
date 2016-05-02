#include"rzadka.h"


double wartosc(rzadka *T,int i,int j)
{
    int k=0;
    while(1)
    {
        if(T->wiersze[k]==-1)return 0;
        if(T->wiersze[k]==i)
        {
            int p=0;
            while(1)
            {
                if(T->liczby[k][p].y==-1)break;
                if(T->liczby[k][p].y==j)return T->liczby[k][p].x;
                p++;
            }
        }
        k++;
    }
}
rzadka *dodaj_e(rzadka *T,double z,int i,int j)
{
    int k=0;
    while(1)
    {
        if(T->wiersze[k]==-1)
        {
            T->wiersze[k]=i;
            //T-
        }
        if(T->wiersze[k]==i)
        {
            int p=0;
            while(1)
            {
                if(T->liczby[k][p].y==-1)
                {
                    T->liczby[k][p].y = j;
                    T->liczby[k][p].x = z;
                    T->liczby[k][p+1].y = -1;
                    return T;
                }
                if(T->liczby[k][p].y==j)
                {
                    T->liczby[k][p].x = z;
                    return T;
                }
                p++;
            }
        }
        k++;
    }
}
rzadka *trasponuj(rzadka *T)
{


}
