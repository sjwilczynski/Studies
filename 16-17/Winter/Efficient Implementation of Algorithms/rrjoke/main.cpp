#include <bits/stdc++.h>
using namespace std;

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
    int t;
    cin >> t;
    while(t--){
    	int n,a,b;
    	cin >> n;
    	for(int i = 0; i < n; i++){
    		cin >> a >> b;
    	}
    	int res = 1;
    	for(int i = 2; i <= n; i++){
    		res = res ^ i;
    	}
    	cout << res << "\n";
    }

	return 0;
}