//Stanislaw Wilczynski Zadanie 2 lista 5 272955 20.11.2014
#include<stdio.h>

int compare(char *t1,char *t2)
{
    if(t1[0]!=t2[0])return t1[0]-t2[0];
    if(t1[1]!=t2[1])return t1[1]-t2[1];
    int i=4;
    while(t1[i]==t2[i] && t1[i]!=0 && t2[i]!=0 && i<34)i++;
    if (t1[i] == 0 && t2[i] != 0) return -1;
    else if (t2[i] == 0 && t1[i] != 0)return 1;
    else return t1[i] - t2[i];
    return 0;
}
void bsort(char *tab[], int roz)
{
    int change;
    for (int i=0; i<roz-1; i++)
    {
        change=0;
        char *w1;
        char *w2;
        for (int j=0; j<roz-1-i; j++)
        {
            w1=tab[j+1];
            w2=tab[j];
            if (compare(w1,w2)<=0)
            {
                tab[j] = w1;
                tab[j+1] = w2;
                change=1;
            }
        }
        if(!change) break;
    }
}

int main()
{
    char tab[10002][40];
    int n;
    for(n=0; !feof(stdin); n++) scanf("%s",tab[n]);
    char *wsk[10002];
    for(int i=0; i<10002; i++)wsk[i]=tab[i];
    bsort(wsk, n-1);
    for(int j=0; j<n-1; j++) printf("%s\n",wsk[j]);
    return 0;
}
