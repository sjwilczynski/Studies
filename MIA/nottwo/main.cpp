#include <bits/stdc++.h>
using namespace std;
const int stala = 1003;
bool tab[stala][stala];

bool isValid(int i, int j){
	bool valid = true;
	if(i > 2){
		if(tab[i-2][j]){
			valid = false;
		}
	}
	if( j > 2 ){
		if( tab[i][j-2] ){
			valid = false;
		}
	}
	return valid;
}


int main(){
	
	//ios::sync_with_stdio(false);
    //cin.tie(NULL);
    int res = 0;
	int A,B;
	cin >> A >> B;
	for(int i = 1; i <= A; i++){
		for(int j = 1; j <= B; j++){
			if(isValid(i,j)){
				res += 1;
				tab[i][j] = true;
			}
		}
	}
	cout << res << "\n";
	return 0;
}