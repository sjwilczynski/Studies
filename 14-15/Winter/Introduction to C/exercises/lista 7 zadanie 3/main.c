#include<stdio.h>
#include<stdlib.h>

int b=0;
void wypisz(int *t,int n)
{
    for(int i=0; i<n; i++)printf("%d ",t[i]);
    printf("\n");
}
int compare (const void * a, const void * b)
{
    return ( *(int*)a - *(int*)b );
}

void wynik(int *t1p,int *t2p,int *t1k,int *t2k)
{
    if(t1p>t1k || t2p>t2k)return;
    if(*t2k>*t1k)wynik(t1p+1,t2p,t1k,t2k-1);
    else
    {
        b++;
        wynik(t1p,t2p,t1k-1,t2k-1);
    }
}

int main()
{
    int tab1[27];
    int tab2[27];
    for(int i=0; i<26; i++)scanf("%d",&tab1[i]);
    qsort(tab1,26,sizeof(int),compare);
    //wypisz(tab1,26);
    //system("PAUSE");
    int k=0;
    for(int i=1; i<=tab1[0]-1; i++)
    {
        tab2[k]=k+1;
        k++;

    }
    for(int i=0; i<25; i++)
    {
        if(k==27)break;
        if(tab1[i]!=(tab1[i+1]+1))
        {
            for(int j=1; j<(tab1[i+1]-tab1[i]); j++)
            {
                tab2[k]=tab1[i]+j;
                k++;
            }
        }
    }
    for(int i=1; i<=52-tab1[25]; i++)
    {
        tab2[k]=tab1[25]+i;
        k++;
        if(k==27)break;
    }
    //wypisz(tab1,26);
    //wypisz(tab2,26);
    wynik(tab1,tab2,tab1+25,tab2+25);
    printf("%d ",b);
    return 0;
}
