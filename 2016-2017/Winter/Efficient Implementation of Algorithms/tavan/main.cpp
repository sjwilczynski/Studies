#include <bits/stdc++.h>
using namespace std;

int main(){

	//ios::sync_with_stdio(false);
    //cin.tie(NULL);
	int N,M,K,X;
	cin >> N >> M >> K >> X;
	char znaki[M][K];
	char slowo[N];
	vector<int> gwiazdki;
	gwiazdki.resize(0);
	for(int i = 0; i < N; i++){
		cin >> slowo[i];
		if( slowo[i] == '#'){
			gwiazdki.push_back(i);
		}
	}
	for(int i=0; i < M; i++){
		for(int j = 0; j < K; j++){
			cin >> znaki[i][j];
		} 
	}
	for(int i = 0; i < M; i++){
		sort(znaki[i],znaki[i]+K);
	}
	for(int i = 0; i < M; i++){
		int indeks = 0;
		if(X > 0){
			indeks = X%K - 1;
		} 
		if(indeks < 0){
			indeks += K;
		}
		slowo[ gwiazdki[ M-1-i ] ] = znaki[M-1-i][indeks];
		if(X/K * K == X){
			X = X/K;
		} else{
			X = X/K + 1;
		}
	}
	for(int i = 0; i < N; i++){
		cout << slowo[i];
	}
	cout << "\n";
	return 0;
}