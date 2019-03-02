#include <bits/stdc++.h>
using namespace std;

const int stala = 4;
const long long MOD = 1000000000;

struct matrix{

	int row;
	int column;
	long long tab[stala][stala];

	matrix(): row(0), column(0){}
	matrix(int n, int m):
		row(n), column(m){
			for(int i = 0; i < row; i++){
				for(int j = 0; j < column; j++){
					tab[i][j] = 0;
				}
			}
		}
	matrix(long long* arr):
		row(stala),column(stala){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				tab[i][j] = arr[i*column+j];
			}
		}
	}
	
	void fill(long long* arr){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < column; j++){
				tab[i][j] = arr[i*column+j];
			}
		}
	}
	matrix operator *(const matrix& m2){
		matrix m(row,m2.column);
		for(int i = 0; i < row; i++){
			for(int j = 0; j < m2.column; j++){
				for(int k = 0; k < column; k++){
					m.tab[i][j] = ( m.tab[i][j] + ( tab[i][k]*m2.tab[k][j] ) % MOD ) % MOD; 
				}
			}
		}
		return m;
	}
};
ostream& operator <<(std::ostream& stream, const matrix& m) {
	for(int i = 0; i < m.row; i ++){
    	for(int j = 0; j < m.column; j++){
    		stream << m.tab[i][j] << " ";
    	}
    	stream << "\n";
    }
	return stream;
}

matrix fastPow(matrix& m1, long long n){
	if( n == 1 ){
		return m1;
	}
	long long arr[16] = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
	matrix res(arr);
	//cout << res;
	for(long long k = 1; k <= n; k*=2){
		if(n & k){
			res = res * m1;
		}
		m1 = m1 * m1;
	}
	return res;


}



int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
    long long n;
    long long v1[16] = {1,1,0,0,0,1,1,1,0,1,0,0,0,0,0,1};
    matrix m1(4,1);
    m1.tab[0][0] = 1;
    m1.tab[1][0] = 1;
    m1.tab[2][0] = 1;
    m1.tab[3][0] = 1;
    long long result;
    for(int i = 0; i < 30; i++){
    	cin >> n;
    	matrix m(v1);
    	if(n == 0){
    		result = 1; 
    	} else{
    		result = (fastPow(m,n)*m1).tab[0][0];
    	}
    	
    	cout<<setfill('0') << setw(9)<< result <<"\n";
    }

	return 0;
}