#include<stdio.h>
#include<stdlib.h>
int f(int n)
{
    // printf("!");
    int r;
    if(205>n)r=205;
    else r=n;
    int t[10][r];
    int d[]= {0, 1, 2, 5, 10, 20, 50, 100, 200};
    for(int i=1; i<=8; i++)t[i][0]=1;
    for(int i=1; i<=n; i++)t[1][i]=1;
    for(int i=2; i<=8; i++)
    {
        for(int x=1; x<d[i]; x++)t[i][x]=t[i-1][x];
        //if(d[i]>n)continue;
        for(int j=0; j<d[i]; j++)
        {
            for(int k=d[i]; j+k<=n; k=k+d[i])
                t[i][j+k]=t[i][j+k-d[i]]+t[i-1][j+k];
        }
    }
    return t[8][n];
}
int main()
{
    int a;
    scanf("%d",&a);
    for(int i=1;i<=a;i++)printf("%d - %d\n",i,f(i));
    return 0;
}
