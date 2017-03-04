#include <bits/stdc++.h>
using namespace std;

int main(){
	
	//ios::sync_with_stdio(false);
    //cin.tie(NULL);
	vector<int> tab;
	tab.resize(100002,-1);
	int n,x,k;
	int result = 1;
	cin >> n;
	for(int i = 0; i < n; i++){
		cin >> x >> k;
		if(tab[k] < x - 1){
			result = 0;
		} else{
			tab[k] = max(tab[k],x);
		}
	}

	if(result) cout << "YES\n";
	else cout << "NO\n";
	return 0;
}