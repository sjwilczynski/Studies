#include <cstdio>
#include <cstdlib>

using namespace std;

class macierz
{
public:
    long long a,b,c,d;
    macierz(long long a1,long long a2, long long a3,long long a4)
    {
        a = a1;
        b = a2;
        c = a3;
        d = a4;
    }
    macierz mnozenie( macierz m, long long p)
    {
        long long a1 = ((a*m.a)%p + (b*m.c)%p)%p;
        long long a2 = ((a*m.b)%p + (b*m.d)%p)%p;
        long long a3 = ((c*m.a)%p + (d*m.c)%p)%p;
        long long a4 = ((c*m.b)%p + (d*m.d)%p)%p;
        return macierz(a1,a2,a3,a4);
    }
    macierz dopotegimod(long long n,long long m)
    {
        if(n == 1) return macierz(0,1,1,1);
        else
        {
            if(n%2 == 1)
            {
                macierz x = macierz(0,1,1,1).dopotegimod((n-1)/2,m);
                x = x.mnozenie(x,m);
                x = x.mnozenie(macierz(0,1,1,1),m);
                return x;
            }
            else
            {
                macierz x = macierz(0,1,1,1).dopotegimod(n/2,m);
                x = x.mnozenie(x,m);
                return x;
            }
        }
    }
};

int main()
{
    int T,n,m;
    scanf("%d",&T);
    for(int i = 1; i<=T; i++)
    {
        scanf("%d %d",&n,&m);
        macierz a(0,1,1,1);
        macierz b = a.dopotegimod(n,m);
        //printf("%d %d %d %d\n",b.a,b.b,b.c,b.d);
        printf("%d\n",b.c);
    }
    return 0;
}
