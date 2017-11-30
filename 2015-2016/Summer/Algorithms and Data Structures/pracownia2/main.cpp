#include <iostream>
#include <cstdio>
#include <cstdlib>

using namespace std;

long long wynik[3][5005];
long long pole[3][5005];
char cyfry[5001];
int w,k;

long long potega7(int i)
{
    long long res = 1;
    for(int j = 1; j <= i; j++)
        res *= 7;
    return res;
}

void skanuj(int i)
{
    fgets(cyfry,k+2,stdin);
    for(int j = 2; j <= k+1; j++) pole[i][j] = potega7( (int)(cyfry[j-2]-48) );
    //for(int j = 2; j <= k+1; j++) printf("%ld ",pole[i][j]);
    //printf("\n");
    return;
}

long long mymax(long long a, long long b)
{
    if(a > b) return a;
    else return b;
}

int main()
{
    scanf("%d %d",&w,&k);
    char c;
    scanf("%c",&c);
    skanuj(0);
    skanuj(1);
    for(int j = 2;j <= k+1; j++)
    {
        wynik[0][j] = pole[0][j];
        wynik[1][j] = pole[1][j];
    }
    for(int i = 2; i < w; i++)
    {
        skanuj(i%3);
        for(int j = 2; j <= k+1; j++)
        {
            wynik[i%3][j] = mymax(wynik[(i-2)%3][j-1],wynik[(i-2)%3][j+1]) + pole[i%3][j];
        }
        for(int j = 2; j <= k+1; j++)
        {
            wynik[(i-1)%3][j] = mymax(wynik[i%3][j-2],wynik[i%3][j+2]) + wynik[(i-1)%3][j];
        }
    }

    for(int j = 0; j <= k+1 ; j++)
          printf("%ld ",wynik[(w-1)%3][j]);

    long long result = 0;
    int idx = (w-1)%3;
    for(int j = 2; j < k+2; j++)
    {
        if(wynik[idx][j] > result)result = wynik[idx][j];
    }
    printf("%lld",result);
    return 0;
}