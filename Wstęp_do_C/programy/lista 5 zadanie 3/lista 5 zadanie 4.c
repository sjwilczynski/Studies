//Stanis³aw Wilczynski 272955 lista 5 zadanie 3
#include<stdio.h>

int min(int x, int y)
{
    if(x>y)return y;
    return x;
}
int main()
{
    int n,a,b,c,na,nb,nc,x,y,z;
    scanf("%d",&n);
    scanf("%d %d %d",&a,&b,&c);
    n--;
    while(n--)
    {
        scanf("%d %d %d",&x,&y,&z);
        na = x+min(b,c);
        nb = y+min(a,c);
        nc = z+min(a,b);
        a=na;
        b=nb;
        c=nc;
    }
    printf("%d\n",min(min(na,nb),nc));
    return 0;
}
