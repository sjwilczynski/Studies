#include <cstdio>
#include <cstdlib>
#include <vector>

using namespace std;
int n,m,a,b;
vector < vector<int> > G;
vector<int> wejscie;
vector<int> wyjscie;
vector<int> visit;
int indeks = 0;

void czytajGraf()
{
    scanf("%d %d",&n,&m);
    G.resize(0);
    G.resize(n+2);
    wejscie.resize(0);
    wejscie.resize(n+1);
    wyjscie.resize(0);
    wyjscie.resize(n+1);
    visit.resize(0);
    visit.resize(n+1);
    for(int i=1; i <= m; i++)
    {
        scanf("%d %d",&a,&b);
        G[a].push_back(b);
        G[b].push_back(a);
    }
}
void DFS(int u)
{
    wejscie[u] = indeks;
    visit[u] = 1;
    indeks++;
    for(int i=0; i < G[u].size(); i++)
    {
        int x = G[u][i];
        if(visit[x] == 0)DFS(x);
    }
    wyjscie[u] = indeks;
    indeks++;
}
int main()
{
    czytajGraf();
    DFS(1);
    for(int i=1; i<=n; i++)
    {
        printf("%d ",(wyjscie[i]-wejscie[i]+1)/2);
    }
}
