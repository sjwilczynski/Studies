#include<stdio.h>

int main()
{
    while(2>0)
    {
        int x,y,i;

        scanf("%d",&x);
        if(x==0)break;
        else
        {
            for(i=2;i*i<=x;i++)
            {
                y=x%i;
                if(y==0)
                {
                    printf("%d ",x);
                    printf("NIE\n");
                    break;
                }
            }
            if(y!=0)
            {
                printf("%d ",x);
                printf("TAK\n");
            }
        }
    }
    return 0;

}
