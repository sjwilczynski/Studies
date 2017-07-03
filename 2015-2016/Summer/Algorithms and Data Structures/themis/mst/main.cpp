#include <cstdio>
#include <vector>
#include <cstdlib>
#include <algorithm>

using namespace std;


struct trojka
{
    int u;
    int v;
    int c;
    trojka():u{0},v{0},c{0}{}
    trojka(int a, int b, int c):
        u{a},v{b},c{c}{}
    void wypisz()
    {
        printf("(%d,%d,%d)\n",u,v,c);
    }
};

vector< vector< int > > G;
vector< int > color;
int n,m;
vector< trojka > krawedzie;




void readGraph()
{
    scanf("%d %d",&n,&m);
    krawedzie.resize(0);
    G.resize(0);
    G.resize(n+1);

}

bool compare(trojka a, trojka b)
{
    return a.c < b.c;
}

bool dfs(int u)
{
    color[u] = 1;
    for(unsigned int i = 0; i < G[u].size(); i++)
    {
        int v = G[u][i];
        if(color[v] == 0)
        {
            if( dfs(v) ) return true;
        }
        else
        {
            if( color[v] == 2) return true;
        }
    }
    color[u] = 2;
    return false;
}

bool isCycle( int u )
{
    color.resize(0);
    color.resize(n+1,0);
    return dfs(u);
}


int main()
{
    int u,v,c;
    readGraph();
    for(int i = 0; i< m ; i++)
    {
        scanf("%d %d %d",&u,&v,&c);
        krawedzie.push_back(trojka(u,v,c));
    }
    sort(krawedzie.begin(), krawedzie.end(), compare);
    int d = 0;
    int it = 0;
    int suma = 0;
    /*
    for(int i = 0; i < n ; i++)
    {
        for(int j = 0; j < G[i].size(); j++)printf("%d ",G[i][j]);
        printf("\n");
    }
    */
    while( (it < m) && (d != n-1) )
    {
        //printf(" %d !\n",suma);
        trojka t = krawedzie[it];
        it++;
        G[t.u].push_back(t.v);
        G[t.v].push_back(t.u);
        if( isCycle(t.u) )
        {
            G[t.u].pop_back();
            G[t.v].pop_back();
        }
        else
        {
            suma += t.c;
            d++;
        }
    }
    printf("%d\n", suma);
    return 0;
}
