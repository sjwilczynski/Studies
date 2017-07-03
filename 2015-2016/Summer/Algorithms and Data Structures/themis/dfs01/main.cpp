#include <cstdio>
#include <cstdlib>
#include <vector>

using namespace std;
int n,m,a,b;
vector < vector<int> > G;
vector<int> szefowie;
vector<int> wejscie;
vector<int> wyjscie;
int indeks = 0;

void czytajGraf()
{
    scanf("%d %d",&n,&m);
    int deg[n+2];
    for(int i = 1;i <= n;i++)
    {
        deg[i] = 0;
    }
    G.resize(0);
    G.resize(n+2);
    szefowie.resize(0);
    wejscie.resize(0);
    wejscie.resize(n+1);
    wyjscie.resize(0);
    wyjscie.resize(n+1);
    for(int i=1; i <= m; i++)
    {
        scanf("%d %d",&a,&b);
        G[a].push_back(b);
        deg[b]++;
    }
    for(int i=1; i<=n; i++)
    {
        if(deg[i]==0)szefowie.push_back(i);
    }
}
void DFS(int u)
{
    wejscie[u] = indeks;
    indeks++;
    for(int i=0; i < G[u].size(); i++)
    {
        int x = G[u][i];
        DFS(x);
    }
    wyjscie[u] = indeks;
    indeks++;
}
int main()
{
    czytajGraf();
    //for(int i=0;i<szefowie.size();i++)printf("%d ",szefowie[i]);
    for(int i=0;i<szefowie.size();i++)DFS(szefowie[i]);
    int k,u,v;
    scanf("%d",&k);
    for(int i=1; i<=k; i++)
    {
        scanf("%d %d",&u,&v);
        //printf("%d %d %d %d\n",wejscie[u],wyjscie[u],wejscie[v],wyjscie[v]);
        if((wejscie[u] < wejscie[v]) && (wyjscie[v] < wyjscie[u])) printf("TAK\n");
        else printf("NIE\n");
    }
}
