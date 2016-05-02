//Stanislaw Wilczynski lista 8 zadanie 2 272955 9.12.2014
#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>
#include"drzewo.h"

int main()
{
    int a;
    wezel *drzewo = init();
    wezel *drzewo1 = init();
    wezel *drzewo2 = init();
    stos *st = NULL;
    char c;
    //c = getchar();
    //putchar(c);
    //c =getchar();
    //putchar(c);
    while(1)
    {
        c = getchar();
        if(c==EOF) break;
        if(c=='\n' && c==' ')continue;
        if(c>='0' && c<='9')
        {
            //printf("!");
            a = c - '0';
            while(c>='0' && c<='9')
            {
                c=getchar();
                if(c>='0' && c<='9')a = 10*a + c -'0';
            }
            //printf("%d ",a);
            drzewo1 = dodajl(a);
            //printf("!");
            st = push(st,drzewo1);
            //printf("!");
        }
        if(c=='=')
        {
            wypiszdrzewo(drzewo);
            printf("\n");
        }
        else if(c!=' ' && c!='\n')
        {
            drzewo1 = top(st);
            st = pop(st);
            drzewo2 = top(st);
            st = pop(st);
            drzewo = dodajz(drzewo1,drzewo2,c);
            st = push(st,drzewo);
        }
    }
    return 0;
}
