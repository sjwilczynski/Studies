#include <cstdlib>
#include <cstdio>

using namespace std;

long long poteguj(long long a, long long b, long long m)
{
    if(b == 1) return a%m;
    else
    {
        if(b%2 == 1)
        {
            long long x = poteguj(a,(b-1)/2,m);
            long long y = x*x;
            y = y%m;
            y = y*a;
            y = y%m;
            return y;
        }
        else
        {
            long long x = poteguj(a,b/2,m);
            long long y = x*x;
            y = y%m;
            return y;
        }
    }
}
int main()
{
    int n,a,b,m;
    scanf("%d ",&n);
    for(int i=1; i <= n; i++)
    {
        scanf("%d %d %d",&a,&b,&m);
        printf("%d\n", poteguj(a%m,b,m));
    }
    return EXIT_SUCCESS;
}
