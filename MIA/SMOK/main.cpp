#include <cstdio>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int n,m,a,b;
vector < vector<int> > G;

vector<int> dist;
const int INF=1000007;

void readGraph()
{
    scanf("%d %d",&n,&m);
    G.resize(0);
    G.resize(n+5);

    for(int i=0; i<m; i++)
    {
        scanf("%d %d",&a,&b);
        G[a].push_back(b);
        G[b].push_back(a);
    }
    for(int i = 1; i <= n; i++)
        sort(G[i].begin(),G[i].end());
}


bool BFS(int s)
{
    bool spojny = true;
    dist.resize(0);
    dist.resize(n+1,INF);
    dist[s]=0;
    queue<int> Q;
    Q.push(s);
    while(!Q.empty())
    {
        int niev = 0;
        int u=Q.front();
        Q.pop();
        unsigned int j = 0;
        if(G[u].size() > 0) niev = G[u][j];
        for (int v = 1; v <= n; v++)
        {
            if(dist[v] != INF) continue;
            if(v > niev && j < G[u].size() - 1){
                j++;
                niev = G[u][j];
            }
            if(v == niev) continue;
            if(dist[u] + 1 < dist[v])
            {
                Q.push(v);
                dist[v]=dist[u]+1;
            }
        }
    }


    for(int j=1; j<=n; j++)
        if(dist[j]==INF)
        {
            spojny = false;
            break;
        }
    return spojny;
}


int main()
{
    int T;
    scanf("%d",&T);
    for(int tt = 0; tt < T; tt++)
    {
        readGraph();
        if(BFS(1)) printf("TAK\n");
        else printf("NIE\n");

    }
    return 0;
}
