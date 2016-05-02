#include<stdio.h>

int MAX = 1000;


int main()
{
    int t[MAX+7];
    int i,j,ostatni=0;
    for(i=1;i<=MAX+6;i++)t[i]=0;
    int n,m;
    int c=12;

    while(2>0)
    {
        //printf("%d,n");

        scanf("%d",&n);
        c = getchar();
        if(c=='-')
        {
            scanf("%d",&m);
            for(j=n;j<=m;j++)t[j]=1;
            ostatni = m;

        }
        else t[n]=1;

        if(n>ostatni)ostatni = n;
        if(c==';')break;

    }
    for(i=0;i<=ostatni;i++)
    {
        if(t[i]==1)
        {
            printf("%d ",i);
            while(2>0)
            {
                c=getchar();
                if(c==EOF) return 0;
                putchar(c);
                if(c=='\n')break;
            }
        }
        else
        {
            while(2>0)
            {
                c=getchar();
                if(c==EOF) return 0 ;
                if(c=='\n')break;
            }
        }
        //if(i==14 && ostatni == 20)return 0;
    }

    return 0;
}
