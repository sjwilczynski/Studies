#include<stdio.h>
#include<math.h>

int n;
int t[1005][3];
int dlugosc(int d1[])
{
    double wynik = 0;
    int x1,y1,x2,y2,xd,yd;
    for(int i=1;i<n;i++)
    {
        x1 = t[d1[i]][1];
        y1 = t[d1[i]][2];
        x2 = t[d1[i+1]][1];
        y2 = t[d1[i+1]][2];
        xd = x1-x2;
        yd = y1-y2;
        wynik += sqrt(xd*xd + yd*yd);
    }
    x1 = x2;
    y1 = y2;
    x2 = t[d1[1]][1];
    y2 = t[d1[1]][2];
    xd = x1-x2;
    yd = y1-y2;
    wynik += sqrt(xd*xd + yd*yd);
    int w=wynik;
    return w;
}
void zamiana(int a, int b,int nd[],int d[])
{
    for(int i=1;i<=a;i++)nd[i]=d[i];
    for(int i=a+1;i<=b;i++)nd[i]=d[b+a+1-i];
    for(int i=b+1;i<=n;i++)nd[i]=d[i];
}

int main()
{
    int x,k;
    scanf("%d",&n);
    int droga[n+3];
    int ndroga[n+3];
    for(int i=1;i<=n;i++)
    {
        scanf("%d",&x);
        scanf("%d",&k);
        t[x][1]=k;
        scanf("%d",&k);
        t[x][2]=k;
    }
    for(int i=1;i<=n;i++)scanf("%d",&droga[i]);
    int best = dlugosc(droga);
    printf("%d\n",best);
    int p = 0;
    while(1)
    {
        p=-1;
        for(int i=1;i<=n-1;i++)
        {
            for(int k=i+1;k<=n;k++)
            {
                zamiana(i,k,ndroga,droga);
                int nbest = dlugosc(ndroga);
                if(nbest<best)
                {
                    p=0;
                    for(int j=1;j<=n;j++)droga[j]=ndroga[j];
                    best = nbest;
                }
            }
        }
        if(p==-1)break;
    }
    printf("%d",best);
    return 0;
}
