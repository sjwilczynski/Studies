#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

int INF = 2000007;
vector< int > G[100002];
vector< short > Gwagi[100002];
int dist[100002];


struct kopiec
{
	pair< int, int > tab[100002];
	int indeks[100002];
	int rozmiar;
	kopiec(int n):
		rozmiar{n}{}
	kopiec(){}
	void delete_min()
	{
		indeks[tab[1].second] = -1;
		swap(tab[1],tab[rozmiar]);
		rozmiar--;
		przesun_nizej(1);
	}
	pair <int,int> min()
	{
		return tab[1];
	}
	void zmien(int i, int odl)
	{
		int stara = tab[i].first;
		tab[i].first = odl;
		if(odl < stara) 
			przesun_wyzej(i);
		else
			przesun_nizej(i); 
	}
	void przesun_nizej(int i)
	{
		int k = i;
		int j = -1;
		while( k != j )
		{
			j = k;
			if(2*j <= rozmiar && tab[2*j].first < tab[k].first)
				k = 2*j;
			if(2*j < rozmiar && tab[2*j + 1].first < tab[k].first)
				k = 2*j+1;
			swap(tab[k],tab[j]);
			indeks[tab[j].second] = j;
			indeks[tab[k].second] = k;						
		}
	}
	void przesun_wyzej(int i)
	{
		int k = i;
		int j = -1;
		while( k != j )
		{
			j = k;
			if(j > 1 && tab[j/2] > tab[k])
				k = j/2;
			swap(tab[k],tab[j]);
			indeks[tab[j].second] = j;
			indeks[tab[k].second] = k;							
		}
	}

};



int main()
{
	//cout << sizeof(kopiec);
    int k,n,m,a,b,waga;
    long long droga = 0;
	ios::sync_with_stdio(false);
    cin.tie(NULL);
    cin >> n >> m >> k;
	for(int i = 0; i <= n; i++)
		dist[i] = INF;
    for(int i=0; i < m; i++)
    {
        cin >> a >> b >> waga;
        G[a].push_back(b);
		Gwagi[a].push_back(waga);
        G[b].push_back(a);
		Gwagi[b].push_back(waga);
    }
    dist[1]=0;
	kopiec K(n);
	K.tab[1] = pair<int,int>(0,1);
	K.indeks[1] = 1;
	
	for(int i = 2; i <= n; i++)
	{
		K.tab[i] = pair<int,int>(INF,i);
		K.indeks[i] = i;
	}     

    while( K.rozmiar )
    {
		 //cout << "iteracja\n";
         pair <int, int> u;
 		 u = K.min();
		 K.delete_min();
		 //cout << u.second << " " << K.rozmiar << "\n";
         int v = u.second;
         if(dist[v] == INF || v == 1)
         {
                  dist[v] = u.first;
				  int sas = G[v].size();
                  for(int j = 0; j < sas; j++)
				  {
					int p = K.indeks[ G[v][j] ];
					if(p == -1) continue;
                  	if(K.tab[p].first > Gwagi[v][j] + dist[v])
				  	{
						if(K.tab[p].second != G[v][j]) cout << "BUUU\n"; 
						K.zmien( K.indeks[ G[v][j] ], Gwagi[v][j] + dist[v]);
					}
				  }
         }
		//for(int j = 1; j <= K.rozmiar; j++)
		//	cout << '(' << K.tab[j].first << ',' << K.tab[j].second << ')' << " ";
		//cout << "\n";	
    }
	//for(int i = 1; i <= n; i++)
		//cout << i << " " << dist[i] << "\n";
    for(int i = 0; i < k; i++)
    {
        cin >> a;
        if(dist[a] == INF)
        {
            cout << "NIE" << "\n";
            return 0;
        }
        droga += (long long) dist[a];
    }
    cout << (droga << 1) << "\n";
    return 0;
}
