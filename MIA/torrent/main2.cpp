#include <bits/stdc++.h>
using namespace std;
int n,a,b;
struct Graph{

	struct Vertex{
		vector<int> edges;
		int pre;   //na potrzeby zadania
		Vertex(): pre(-1){}
	};

	vector<Vertex> vertices; 
	Graph(int n){
		vertices.assign(n,Vertex());
	}

	void add(int from, int to){
		vertices[from].edges.push_back(to);
		vertices[to].edges.push_back(from);
	}

	void erase(int from, int to){
		int len = vertices[from].edges.size();
		for(int i = 0; i < len; i++){
			if(vertices[from].edges[i] == to){
				swap(vertices[from].edges[i],vertices[from].edges[len-1]);
				vertices[from].edges.pop_back();
				break;
			}
		}
		len = vertices[to].edges.size();
		for(int i = 0; i < len; i++){
			if(vertices[to].edges[i] == from){
				swap(vertices[to].edges[i],vertices[to].edges[len-1]);
				vertices[to].edges.pop_back();
				break;
			}
		}
	}
	int dfs(int current, int from){
		vector<int> result;
		vector<int> tmpEdges = vertices[current].edges; //z tym cos by mozna zrobic
		for(int i = 0; i < tmpEdges.size(); i++){
			int v = tmpEdges[i];
			if(v != from){
				vertices[v].pre = current;
				result.push_back(dfs(v,current));
			}
		}
		sort(result.begin(),result.end());
		int len = result.size();
		int res = 0;
		for(int i = len - 1; i >= 0; i--){
			res = max(res,result[i] + len - i);
		}
		return res;
	}

	bool calculate_result(int mid, int& res, vector<int>& path){
		if(mid > 0){
			erase(path[mid-1],path[mid]);
			int res1 = dfs(a,-1);
			int res2 = dfs(b,-1);
			//cout << mid << " " << res2 << " " << res1 << "\n";
			res = min(res,max(res1,res2));
			add(path[mid-1],path[mid]);
			return res2 < res1;
		} else{
			return false;
		}
	}

	int solve(){
		int res = dfs(a,-1);
		vector<int> path;
		for(int u = b; u != a; u = vertices[u].pre){
			path.push_back(u);
		}
		path.push_back(a);
		int sta = 0;
		int end = path.size();
		while(sta < end){
			int mid = (sta+end)>>1;
			if(calculate_result(mid,res,path)){
				sta = mid+1;
			} else{
				end = mid;
			}
		}
		return res;
	}

};

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
    cin >> n >> a >> b;
    int x,y;
    Graph g = Graph(n+1);
    for(int i = 0; i < n-1; i++){
    	cin >> x >> y;
    	g.add(x,y);
    }
    cout << g.solve() << "\n";

	return 0;
}