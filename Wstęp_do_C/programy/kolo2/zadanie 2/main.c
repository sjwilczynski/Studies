#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<string.h>
#include<math.h>
typedef struct klub{
    char nazwa[100];
    int pop;
    int x;
    int y;
}klub;
double dist(int x1,int y1,int x2,int y2)
{
    return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}
int main(int argc,char *argv[])
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
    for(int i=1;i<liczba-1;i++)printf("%d ",tab[i].pop);
    printf("\n");

    int a,b,pmin;
    sscanf(argv[1],"%d",&a);
    sscanf(argv[2],"%d",&b);
    sscanf(argv[3],"%d",&pmin);

    printf("%d %d %d\n",a,b,pmin);
    int pier=0;
    int drugi=0;
    double ost = 10000001;
    double przedost = 10000000;
    for(int i=1;i<liczba;i++)
    {
        if(tab[i].pop>=pmin)
        {
            //printf("!");
            double z = dist(a,b,tab[i].x,tab[i].y);
            if(z<przedost)
            {
                drugi = pier;
                pier = i;
                ost = przedost;
                przedost = z;
            }
            else if(z<ost)
            {
                drugi = i;
                ost = z;
            }
        }
    }
    printf("%d %d\n",pier,drugi);
    fputs(tab[pier].nazwa,stdout);
    printf(" %d %d ",tab[pier].x,tab[pier].y);
    printf("\n");
    fputs(tab[drugi].nazwa,stdout);
    printf(" %d %d ",tab[drugi].x,tab[drugi].y);
    printf("\n");

    fclose(plik);
    return 0;
}
