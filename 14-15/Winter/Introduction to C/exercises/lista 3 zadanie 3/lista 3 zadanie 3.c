#include<stdio.h>

int x,t1,znak;
int sek,min,godz;
const double m = 42.195;
const double h = 21.0975;
int skanc()
{
    int res;
    scanf("%d",&x);
    if(x==0)return -1;
    res=x;
    znak=getchar();
    if(znak==':')
    {
        scanf("%d",&x);
        res=res*60+x;
    }
    else return res;
    znak=getchar();
    //printf("%d  ",znak);
    if(znak==':')
    {
        scanf("%d",&x);
        res=res*60+x;
        znak = getchar();
    }
    return res;
}
void wyp(int L)
{
    //printf("%daaa",L);
    sek=0;min=0;godz=0;
    sek = L%60;
    L=(L-sek)/60;
    //printf("%daaa",L);
    min = L%60;
    L=(L-min)/60;
    //printf("%daaa",L);
    godz = L;
    if(godz>0)printf("%d:",godz);
    if(godz>0 && min<10)printf("0%d:",min);
    if(godz>0 && min>=10) printf("%d:",min);
    if(godz==0 && min>0) printf("%d:",min);
    if(sek<10 && (min>0 || godz>0)) printf("0%d\n",sek);
    else printf("%d\n",sek);
}
int dod(int t)
{
    int w;
    w=skanc();
    return t+w;
}
int pom(int L)
{
    int mnoz = 0;
    znak=getchar();
    while(znak==' ')znak=getchar();
    if(znak == 'm') return L*m;
    if(znak == 'h') return L*h;
    while(znak<='9' && znak>='0')
    {
        mnoz = mnoz*10 + znak-48;
        znak = getchar();
    }
    return L*mnoz;
}
int podz(int L)
{
    int dziel = 0;
    znak=getchar();
    while(znak==' ')znak=getchar();
    if(znak == 'm') return L/m;
    if(znak == 'h') return L/h;
    while(znak<='9' && znak>='0')
    {
        dziel = dziel*10 + znak-48;
        znak = getchar();
    }
    return L/dziel;
}
int main()
{
    //znak = ' ';
    //printf("%d ",znak);
    //znak = '\n';
    //printf("%d ",znak); kod spacji 32, kod enter 10
    while(1)
    {
        t1=skanc();
        //printf("%d ",t1);
        if(t1==-1)break;
        while(znak==' ')znak=getchar();
        //printf("%d ",s);
        if(znak=='+') wyp(dod(t1));
        if(znak=='*') wyp(pom(t1));
        if(znak=='/') wyp(podz(t1));
    }
    return 0;
}
