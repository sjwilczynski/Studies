#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<string.h>
typedef struct klub{
    char nazwa[100];
    int pop;
    int x;
    int y;
}klub;
int main()
{
    FILE *plik;
    plik = fopen("kluby.txt","r");
    char str[100];
    klub tab[100];
    int liczba = 1;
    while(1)
    {
        if(fgets(str,100,plik)==NULL)break;
        int i=0;
        while(str[i]!=' ')
        {
            tab[liczba].nazwa[i]=str[i];
            i++;
        }
        sscanf(str+i,"%d %d %d",&tab[liczba].pop,&tab[liczba].x,&tab[liczba].y);
        liczba++;
    }
    for(int i=1;i<liczba;i++)fputs(tab[i].nazwa,stdout);
    printf("\n");
    for(int i=1;i<liczba;i++)printf("%d ",tab[i].y);
    fclose(plik);
    return 0;
}
