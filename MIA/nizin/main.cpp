#include <iostream>

using namespace std;


int wynik(int* tab, int p, int k){
    if( p == k || k < p ) return 0;
    if( tab[p] == tab[k] ) return wynik(tab,p+1,k-1);
    if( tab[p] < tab[k] ){
        tab[p+1] += tab[p];
        return 1+wynik(tab,p+1,k);
    }
    if(tab[p] > tab[k]){
        tab[k-1] += tab[k];
        return 1+wynik(tab,p,k-1);
    }
}

int main()
{
    int n;
    cin >> n;
    int tab[n+1];
    for(int i = 0; i < n; i++){
        cin >> tab[i];
    }
    cout << wynik(tab,0,n-1);
    return 0;
}
