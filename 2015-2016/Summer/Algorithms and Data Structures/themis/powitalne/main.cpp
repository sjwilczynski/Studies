//Stanisław Wilczyński
//272955    KLO
#include<cstdio>

int main()
{
    int a,b;
    scanf("%d %d",&a,&b);
    if(a%2 == 0)a++;
    while(a <= b)
    {
        printf("%d ",a);
        a +=2;
    }
    return 0;
}


