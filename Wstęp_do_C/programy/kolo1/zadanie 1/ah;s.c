#include<stdio.h>
#include<stdlib.h>

int f(int n)
 {
     int d[]={0,1,2,5,10,20,50,100,200};
     int t[9][n+1];
     for(int i=1;i<=8;i++)t[i][0]=1;
     for(int i=1;i<=n;i++)t[1][i]=1;
     for(int i=2;i<=8;i++)
     {
         for(int x=1;x<d[i];x++)t[i][x]=t[i-1][x];
         //if(d[i]>n)continue;
         for(int j=0;j<d[i];j++)
         {
            for(int k=d[i];j+k<=n;k=k+d[i])
                t[i][j+k]=t[i][j+k-d[i]]+t[i-1][j+k];
         }
     }
     return d[8];
 }

int main()
{
    int a;
    scanf("%d",&a);
    printf("%d ",f(a));
    return 0;
}

