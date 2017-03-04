#include <bits/stdc++.h>
using namespace std;

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
	int n,m,k;
	int a,b;
	cin >> n >> m >> k;
	bool valid[n+1];
	for(int i = 1; i <= n; i++){
		valid[i] = false;
	}
	valid[k] = true;
	for(int i = 0; i < m; i++){
		cin >> a >> b;
		if(valid[a]){
			valid[b] = true;
		}
		if(valid[b]){
			valid[a] = true;
		}
	}
	for(int i = 1; i <= n; i++){
		if(valid[i]){
			cout << "TAK\n";
		} else{
			cout << "NIE\n";
		}
	}
	return 0;
}