#include <bits/stdc++.h>
#include <climits>
using namespace std;
int n,a,b;
struct Graph{

	struct Vertex{
		vector< pair<int,int> > edges;
		//int pre;   //na potrzeby zadania
		//Vertex(): pre(-1){}
	};

	vector<Vertex> vertices; 
	Graph(int n){
		vertices.assign(n,Vertex());
	}

	void add(int from, int to, int weight){
		vertices[from].edges.push_back( make_pair(to,weight) );
		vertices[to].edges.push_back( make_pair(from,weight) );
	}

	void erase(int from, int to){
		int len = vertices[from].edges.size();
		for(int i = 0; i < len; i++){
			if(vertices[from].edges[i].first == to){
				swap(vertices[from].edges[i],vertices[from].edges[len-1]);
				vertices[from].edges.pop_back();
				break;
			}
		}
		len = vertices[to].edges.size();
		for(int i = 0; i < len; i++){
			if(vertices[to].edges[i].first == from){
				swap(vertices[to].edges[i],vertices[to].edges[len-1]);
				vertices[to].edges.pop_back();
				break;
			}
		}
	}
	void a_star(int start, int goal, vector<int>& heuristic){
		priority_queue< pair <int, int> > Q;
		vector<bool> visited;
		vector<int> f,g;
		g.resize(vertices.size(), INT_MAX);
		f.resize(vertices.size(), INT_MAX);
		visited.resize(vertices.size(), false);
		g[start] = 0;
		f[start] = heuristic[start];
		Q.push( make_pair(-f[start],-start) );
		while(!Q.empty()){
			pair<int,int> current = Q.top();
			int curr = -current.second;
			int fscore = -current.first;
			Q.pop();
			if(visited[curr]){
				continue;
			}
			visited[curr] = true;
			cout << curr << " " << fscore << "\n";
			if(curr == goal){
				return;
			}
			for(int i = 0; i < vertices[curr].edges.size(); i++){
				int next = vertices[curr].edges[i].first;
				int weight = vertices[curr].edges[i].second;
				if(visited[next]){
					continue;
				}
				int new_fscore = g[curr] + weight + heuristic[next];
				if(new_fscore < f[next]){
					Q.push(make_pair(-new_fscore,-next));
					f[next] = new_fscore;
					g[next] = g[curr] + weight;
				} 
			}
		}

	}
};

ostream& operator << ( ostream& stream, pair<int,int>& p ){
	stream << "( " << p.first << ", " << p.second << " )";
	return stream;
}
ostream& operator << ( ostream& stream, Graph& G ){
	stream << "Number of nodes: " << G.vertices.size() << "\n";
	stream << "Edges:\n";
	for(int i=0; i < G.vertices.size(); i++){
		stream << i << ": ";
		for(int j=0; j < G.vertices[i].edges.size(); j++){
			stream << G.vertices[i].edges[j] << " ";
		}
		stream << "\n";
	}
	stream << "\n";
	return stream;
}

int main()
{
    int nodes;
    int edges;
    int start;
    int goal;
    vector<int> heuristic;
    cin >> nodes >> edges >> start >> goal;
    heuristic.resize(nodes);
    Graph G = Graph(nodes);
    for (int i = 0; i < nodes; i++) {
        cin >> heuristic[i];
    }
    for (int i = 0; i < edges; i++) {
        int x,y,c;
        cin >> x >> y >> c;
        G.add(x,y,c);
    }
    /*TEST GRAPH
    cout << G;
    G.erase(0,2);
    cout << G;
	*/

	G.a_star(start, goal, heuristic);

	return 0;
}