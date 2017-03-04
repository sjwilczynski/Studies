#include <bits/stdc++.h>
using namespace std;

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
	int n;
	cin >> n;
	string a,b;
	cin >> a >> b;
	n--;
	for(int i = 0; i < n; i++){
		if(b[i] == 'N') b[i] = 'S';
		else if(b[i] == 'S') b[i] = 'N';
		else if(b[i] == 'W') b[i] = 'E';
		else if(b[i] == 'E') b[i] = 'W';
	}
	for(int i = 0; i < n/2; i++){
		swap(b[i],b[n-i-1]);
	}
	string A = "#"+a;
	string B = "#"+b;

	//cout << A << " " << A.size();
	//cout << B << " " << B.size();

	int pi[n+1];
	for(int i = 1; i <= n; i++){
		pi[i] = 0;
	}

	//KMP
	int k = 0;

	for(int q = 2; q <= n; q++){
		while(k > 0 && B[k+1] != B[q]){
			k = pi[k];
		}
		if(B[k+1] == B[q]){
			k++;
		}
		pi[q] = k;
	}

	k = 0;

	for(int i = 1; i <= n; i++){
		while( k > 0 && B[k+1] != A[i]){
			k = pi[k];
		}
		if(B[k+1] == A[i]){
			k++;
		}
	}
	if(k == 0){
		cout << "YES\n";
	} else{
		cout << "NO\n";
	}
	return 0;
}