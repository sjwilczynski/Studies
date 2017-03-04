#include <bits/stdc++.h>
using namespace std;

int main(){
	
	//ios::sync_with_stdio(false);
    //cin.tie(NULL);
	int n;
	cin >> n;
	long long F[n+1][n+1];
	for(int i = 1; i <=n ; i++){
		for(int j = 1; j < n; j++){
			F[i][j] = 0;
		}
	}
	for(long long i = 1; i <= n; i++){
		F[1][i] = i;
	}
	for(int i = 2; i <= n; i++){
		for(int j = 2; j <= i; j++){
			if(j < i){
				F[j][i] = 1 + F[j][i-1] + F[j-1][i];
			} else{
				F[j][i] = 1 + F[j-1][i-1] + F[j-1][i]; 
			} 
		}
	} 
	cout << F[n][n] << "\n";
	return 0;
}