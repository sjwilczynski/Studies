#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int main()
{
    char str[1005];
    fgets(str,10000,stdin);
    //printf("%d ",strlen(str));
    //for(int i=0;i<strlen(str)-1;i++)printf("%c ",str[i]);
    int wierszend = strlen(str)-2;
    int k=0;
    while(k<=wierszend)
    {
        int kon=wierszend;
        int z = wierszend;
        int pocz = k;
        while(pocz<=kon)
        {
            if(str[pocz]==str[kon])
            {
                pocz++;
                kon--;
            }
            else
            {
                wierszend--;
                break;
            }
        }
        if(z==wierszend)break;
    }
    for(int i=0;i<=wierszend;i++)printf("%c",str[i]);
    return 0;
}
