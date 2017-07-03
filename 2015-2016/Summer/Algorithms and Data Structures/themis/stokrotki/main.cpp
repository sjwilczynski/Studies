#include <iostream>
#include <cstdio>
#include <climits>


using namespace std;

int big = INT_MAX;

pair< int, int > wynik[1004][1004];
int pole[1004][1004];

pair<int,int> mymin(int i,int j)  //to przerob
{
    pair<int,int> res;
    if(wynik[i-1][j-1].first > wynik[i][j-1].first)
        res = pair<int,int>(wynik[i][j-1].first,i);
    else res = pair<int,int>(wynik[i-1][j-1].first,i-1);
    if(wynik[i+1][j-1].first < res.first) res = pair<int,int>(wynik[i+1][j-1].first,i+1);
    res.first += pole[i][j];
    return res;
}

int main()
{
    //printf("%d\n",big);
    int n,m;
    scanf("%d %d",&n,&m);
    for(int i = 1; i <= n; i++)
    {
        for(int j = 0; j < m; j++)
        {
            scanf("%d",&pole[i][j]);
        }
    }
    for(int j = 0; j < m; j++)
    {
        wynik[0][j].first = big;
        wynik[n+1][j].first = big;
    }
    for(int i = 1; i <= n; i++) wynik[i][0] = pair< int, int >(pole[i][0],-1);
    for(int j = 1; j < m; j++)
    {
        for(int i = 1; i <= n; i++)
        {
            wynik[i][j] = mymin(i,j);
        }
    }
    //for(int i = 1; i <= n; i++) printf("(%d,%d) ",wynik[i][0].first,wynik[i][0].second);
    //for(int i = 1; i <= n; i++) printf("(%d,%d) ",wynik[i][1].first,wynik[i][1].second);

    int res = big;
    int wsp;
    for(int i = 1; i <= n; i++)
    {
        if(wynik[i][m-1].first < res)
        {
            res = wynik[i][m-1].first;
            wsp = i;
        }
    }
    //printf("%d\n",res);
    int k = m-1;
    int tab[m];
    while( k >= 0 )
    {
        tab[k] = wsp-1;
        wsp = wynik[wsp][k].second;
        k--;
    }
    for(int j = 0; j < m; j++)printf("%d ",tab[j]);
    printf("\n");

    return 0;
}
