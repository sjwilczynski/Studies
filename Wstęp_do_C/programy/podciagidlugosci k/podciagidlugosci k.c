#include<stdio.h>


int main()
{
    int p,k,x,ros,mal,d1,d2; //ros - liczba rosnacych ciagow dlugosci k, analogicznie mal,
    //x wczytywana liczba,p poprzednie wczytana liczba
    //d1, d2 chwilowe dlugosci monotonicznych ciagow
    scanf("%d",&k);
    p=-1;
    d1=0;d2=0;
    ros=0;mal=0;
    x=1;
    while(x!=0)
    {
        scanf("%d",&x);
        if(x==0)break;
        if(x>p)
        {
            d2=1;
            d1++;
            if(d1==k)
            {
                ros++;
                d1--;
            }


        }
        if(x<p)
        {
            d1=1;
            d2++;
            if(d2==k)
            {
                mal++;
                d2--;
            }

        }
        p=x;
    }

    printf("%d %d",ros,mal);
    return 0;
}
