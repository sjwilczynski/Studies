//Stanislaw Wilczynski zadanie 2 lista 2
#include<stdio.h>

void spacje(int a) //funkcja wypisujaca spacje
{
  for (int i=1;i<=a;i++) putchar(' ');
}
int bialy(char b) //funkcja sprawdzajaca czy dany znak jest bialy
{
    if(b==' ' || b=='\n' || b=='\t') return 1;
    else return 0;
}

int main()
{
    int n,m;
    scanf("%d %d",&m, &n);
    int ds=0;   //d³ugoœæ slowa
    int zjt=m-1;  // liczba zajetych miejsc
    char slowo[n-m+5]; // tablica zawierajaca litery wlasnie wczytywanego slowa
    int c,i;
    int p=0;

    while(2>0)
    {
        c=getchar();
        if(c==EOF)return 0;

        while(bialy(c)!=1)
        {
            if(p==0)
            {
                spacje(m-1);
                p=1;
                zjt=m-1;
            }
            while(bialy(c)==0)
            {
                ds++;
                //printf("%d ",ds);
                if(ds>n-m+1)
                {
                    printf("Slowo nie miesci sie w zadanym przedziale kolumn");
                    return 0;
                }
                slowo[ds]=c;
                c=getchar();
                if(c==EOF) return 0;
            }
            if(ds+zjt>n)
            {
                printf("\n");
                spacje(m-1);
                zjt=m-1;
            }
            for(i=1;i<=ds;i++)
            {
                putchar(slowo[i]);
                slowo[i]=0;
                zjt++;
            }
            if(zjt<n)
            {
                printf(" ");
                zjt++;
            }

            else
            {
                printf("\n");
                spacje(m-1);
                zjt=m-1;
            }
            ds=0;
        }

    }
    return 0;
}
