#include <iostream>

using namespace std;

int wynik(int n, int d){
    int res = 0;
    for(int i = d; i*i <= n; i++){
        if(n%i == 0){
            res += wynik(n/i,i) + 1;
        }
    }
    return res;
}

int main()
{
    int n;
    cin >> n;
    cout << wynik(n,2);
    return 0;
}
