#include<stdio.h>

int main()
{
    int n,j;
    scanf("%d",&n);
    for(j=n;j>0;j--)
    {
            int x,y,i;

            scanf("%d",&x);
            for(i=2;i*i<=x;i++)
            {
                y=x%i;
                if(y==0)
                {
                    //printf("%d ",x);
                    printf("NO\n");
                    break;
                }
            }
            if(y!=0)
            {
                //printf("%d ",x);
                printf("YES\n");
            }

    }
    return 0;

}
