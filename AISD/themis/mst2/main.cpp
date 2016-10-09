#include <cstdlib>
#include <iostream>
#include <vector>
#include <queue>
#include <cstdio>

using namespace std;

int main()
{
    int n,m,a,b,waga;
    vector < vector<pair <int, int> > > G;
    vector<bool> visit;
    scanf("%d %d",&n,&m);
    G.resize(0);
    G.resize(n+5);
    visit.resize(0);
    visit.resize(n+1,0);

    for(int i=0;i<m;i++)
    {
            scanf("%d %d %d",&a,&b,&waga);
            G[a].push_back(make_pair(waga,b));
            G[b].push_back(make_pair(waga,a));
    }

    visit[1] = 1;
    int suma = 0;
    priority_queue< pair <int, int> > Q;
    for(unsigned int i = 0; i < G[1].size() ;i++)Q.push( make_pair( -G[1][i].first, G[1][i].second) );
    while(!Q.empty())
    {
         pair <int, int> u;
         u = Q.top();
         Q.pop();
         int v = u.second;
         if(visit[v] == 0)
         {
                  suma +=  -u.first ;
                  visit[v] = 1;
                  for(unsigned int j = 0;j < G[v].size();j++)
                  {
                          if(visit[G[v][j].second] == 0) Q.push(make_pair(-G[v][j].first, G[v][j].second));
                  }
         }
    }
    printf("%d ",suma);
    return 0;
}
