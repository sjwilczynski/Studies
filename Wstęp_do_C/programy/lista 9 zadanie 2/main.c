#include<stdio.h>

typedef struct trojka
{
    int dokad; // ile wiosek dalej
    int godzina; // o ktorej wyplywa
    int ile; // ile plynie
} trojka;

int main()
{
    //int p=12;
    //int q=3;
    //printf("%d %d", q-p,(24-p+q)%24);
    int n,k;
    scanf("%d %d",&n,&k);
    trojka t[n+2][k+2];
    for(int i=1;i<=k;i++)
    {
        for(int j=1;j<=n;j++) scanf("%d",&t[j][i].dokad);
    }
    for(int i=1;i<=k;i++)
    {
        for(int j=1;j<=n;j++) scanf("%d",&t[j][i].godzina);
    }
    for(int i=1;i<=k;i++)
    {
        for(int j=1;j<=n;j++) scanf("%d",&t[j][i].ile);
    }
    /** for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=k;j++)
        {
            printf("%d ",t[i][j].dokad);
            printf("%d ",t[i][j].godzina);
            printf("%d\n",t[i][j].ile);
        }
    }
     */

    int hstart;
    scanf("%d",&hstart);
    int czas[n+2];
    czas[1]=0;
    for(int i=2;i<=n+1;i++)czas[i]=10000000;
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=k;j++)
        {
            int czas_ocz;
            int a = (hstart+czas[i])%24;
            int b = t[i][j].godzina;
            czas_ocz = (24-a+b)%24;
            int T = czas_ocz + t[i][j].ile + czas[i];
            if(T<czas[i+t[i][j].dokad])czas[i+t[i][j].dokad] = T;
        }
    }
    printf("%d\n",czas[n+1]);
    return 0;
}
