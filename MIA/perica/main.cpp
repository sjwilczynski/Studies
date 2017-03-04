#include <iostream>
#include<algorithm>

using namespace std;

int mod = 1000000007;

int main()
{
    int n,k;
    cin >> n >> k;
    int dwumian[n+3][k+1];
    int wartosci[n+3];
    for(int i = 1; i <= n; i++){
        cin >> wartosci[i];
    }
    sort(wartosci+1,wartosci+n+1);
    for(int i = 0; i <= n; i++){
        dwumian[i][0] = 1;
        dwumian[i][1] = i;
    }
    for(int i = 2; i <= k; i++){
        dwumian[i][i] = 1;
    }
    for(int i = 1; i <=n; i++){
        for(int j = 1; j <= min(k-1,i-1); j++){
            dwumian[i][j] = (dwumian[i-1][j] + dwumian[i-1][j-1])%mod;
        }
    }
    /*
    for(int i = 0; i < n; i++){
        for(int j=0; j <= i; j++){
            cout << dwumian[i][j] << " ";
        }
        cout << "\n";
    }
    */
    long long wyn = 0;
    for(int i = k; i <= n; i++){
        wyn = (wyn + (long long)(dwumian[i-1][k-1])*wartosci[i])%mod;
    }
    cout << wyn << "\n";
    return 0;
}
