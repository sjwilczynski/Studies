#include <bits/stdc++.h>
#include <queue>
#include <map>
using namespace std;


bool ascending(vector<int>& u){
	int i;
	for(i = 1; i < u.size(); i++){
		if(u[i-1] > u[i]){
			return false;
		}
	}
	return true;
}

int result(vector<int>& board, int k, int n){

	queue< vector<int> > Q;
    Q.push(board);
    map<vector<int>, int> dist;
    dist[board] = 0;

    while(!Q.empty()){
    	vector<int> u = Q.front();
    	Q.pop();
    	int d = dist[u];
    	if(ascending(u)){
    		return d;
    	}
    	for(int i = 0; i + k <= n; i++){
    		vector<int> v = u;
    		reverse(v.begin()+i, v.begin()+i+k);
    		if(dist.count(v) == 0){
    			dist[v] = d+1;
    			Q.push(v);
    		}
    	}
    }
    return -1;
}


int main(){
	
	//ios::sync_with_stdio(false);
    //cin.tie(NULL);
    int n,k;
    cin >> n;
    vector<int> board;
    board.resize(n);
    for(int i = 0; i < n; i++){
    	cin >> board[i];
    }
    cin >> k;
    cout << result(board,k,n) << "\n";
	return 0;
}