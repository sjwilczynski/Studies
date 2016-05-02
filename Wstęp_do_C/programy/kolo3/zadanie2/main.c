#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

char *wyn;
char *dodaj_silniowe(const char *l1,const char *l2)
{
    for(int i=0;i<=35;i++)  wyn[i]=0;
    int p=0;
    for(int i=0;i<=35;i++)
    {
        char c1,c2;
        //printf("!");
        if(p==1)wyn[i]++;
        p=0;
        if(l1[i]>='A') c1 = l1[i]-'A'+10;
        else c1 = l1[i]-'0';
        if(l2[i]>='A') c2 = l2[i]-'A'+10;
        else c2 = l2[i]-'0';
        char w = wyn[i]+c1+c2;
        //printf("!");
        if(w >= i+2)
        {
            p=1;
            w = w%(i+2)+1;
        }
        //printf("!");
        if(w>=10) w=w-10+'A';
        else w=w+'0';
        wyn[i]=w;
    }
    return wyn;
}
int main()
{
    const char t1[36] = {'0','0','0','0','0','0','6','7','8','9','A','B','B','B','B','B','B','B','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    const char t2[36] = {'1','1','1','1','1','1','1','1','1','1','1','1','2','3','4','5','B','C','3','5','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    //printf("%c ",t1[35]);
    wyn = (char*)malloc(sizeof(char)*36);
    wyn = dodaj_silniowe(t1,t2);
    for(int i=0;i<=35;i++)printf("%c",wyn[i]);
    return 0;
}
