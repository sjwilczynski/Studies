#include<cstdio>
#include<set>
#include<queue>

using namespace std;

int main()
{
    int n,m;
    scanf("%d %d",&n,&m);
    vector<pair<int,int> > neighbours[n];
    for(int i = 0;i<m;i++)
    {
        int a,b,c;
        scanf("%d %d %d",&a,&b,&c);
        a=a-1;
        b=b-1;
        neighbours[a].push_back(make_pair(-c,b));
        neighbours[b].push_back(make_pair(-c,a));
    }
    set<int> set;
    long long total_weight = 0;
    priority_queue<pair<int,int> > queue;
    set.insert(0);
    for(int i = 0;i<neighbours[0].size();i++)
    {
        queue.push(neighbours[0][i]);
    }
    while(set.size()!=n)
    {
        pair<int,int> edge = queue.top();
        queue.pop();
        if(set.count(edge.second)!=0)
            continue;
        set.insert(edge.second);
        total_weight -= edge.first;
        for(int i = 0;i<neighbours[edge.second].size();i++)
        {
            queue.push(neighbours[edge.second][i]);
        }
    }
    printf("%lld\n",total_weight);
    return 0;
}
