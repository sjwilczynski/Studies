#include<stdio.h>

int MAX = 1000;


int main()
{
    int t[MAX+7][2];
    int j;
    for(j=1;j<=MAX+6;j++)t[j][0]=0;t[j][1]=0;
    int n;
    int m;
    int c;
    int i=1;
    scanf("%d",&n);
    while((c=getchar())!=';')
    {
        //printf("%d,n");
        if(c==' ')
        {
            t[i][0]=n;
            t[i][1]=n;
            i++;
        }
        else
        {
            t[i][0]=n;
            scanf("%d",&m);
            t[i][1]=m;
            i++;

        }
        scanf("%d",&n);
    }
    //for(j=1;j<=20;j++)printf("%d %d\n",t[j][0], t[j][1]);

    int liczba=0;
    for(j=1;j<=i;j++)
    {
        while(liczba<t[j][0])
        {
            c=getchar();
            if(c=='\n')liczba++;
        }
        int p=0;
        while(liczba<=t[j][1])
        {
            if(p==0)
                {
                    printf("%d ",liczba);p=1;
            }
            c=getchar();
            putchar(c);
            if(c=='\n')
                {
                    liczba++;p=0;printf("\n");
            }
        }


    }
    return 0;
}
