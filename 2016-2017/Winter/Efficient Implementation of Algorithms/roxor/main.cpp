#include <bits/stdc++.h>
using namespace std;

int n;
int result[3];
const int size = 1<<15 + 1;
bool winning[size];
bool is_in_winning[size];


int transform( int state, int i, int j, int k ) { 
    state ^= (1 << i); 
    state ^= (1 << j); 
    state ^= (1 << k); 
    return state; 
} 
int ifbit( int state, int i ) { 
    return (state >> i)&1; 
}

bool isWinning(int state){

	if(is_in_winning[state]){
		return winning[state];
	}
	is_in_winning[state] = true;
	for(int i = 0; i < n-1; i++){
		if(!ifbit(state,i)) continue;
		for(int j = i+1; j < n; j++){
			for(int k = j; k < n; k++){
				if(!isWinning( transform( state, i, j, k ) ) ){
					//cout << i << " " << j << " " << k << "\n";
					return winning[state] = true;
				}
			}
		}
	}
	return winning[state] = false;
}

void solve(int state, int* piles){
	for(int i = 0; i < n-1; i++){
		if(!piles[i]) continue;
		for(int j = i+1; j < n; j++){
			for(int k = j; k < n; k++){
				if(!isWinning( transform( state, i, j, k ) ) ){
					result[0] = i;
					result[1] = j;
					result[2] = k;
					return;
				}
			}
		}
	}
} 

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
	cin >> n;
	int piles[n];
	for(int i = 0; i < n; i++){
		cin >> piles[i];
	}
	int state = 0;
	for(int i = n-1; i >= 0; i--){
		state = (state<<1) + piles[i]%2;
	}
	//cout << state << "\n";
	solve(state, piles);
	if(result[1] == 0){
		cout << "NO\n";
	} else{
		cout << "YES\n";
		cout << result[0] << " " << result[1] << " " << result[2] << "\n"; 
	}
	return 0;
}