#include <bits/stdc++.h>
using namespace std;


int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
	int n,k;
	cin >> n >> k;
	if(2 * k + 1 > n){
		cout << -1 << "\n";
	} else{
		cout << n*k << "\n";
		for(int i = 1; i <= n; i++){
			for( int j = 1; j <= k; j++){
				int next = i+j;
				if( next > n) {
					next = next % n;
				}
				cout << i << " " << next << "\n";
			}
		}
	}

	return 0;
}