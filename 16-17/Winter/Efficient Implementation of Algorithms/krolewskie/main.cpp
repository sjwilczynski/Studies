#include <iostream>
#include <vector>

using namespace std;

bool czyKrolewska(int liczba)
{
    int wynik = 0;
    while(liczba > 0)
    {
        wynik += (liczba%2);
        liczba = liczba >> 1;
    }
    if(wynik % 2 == 0) return true;
    return false;
}


int main()
{
    int t,k;
    cin >> t;
    for(int i = 0; i < t; i++){
        cin >> k;
        int result = 2*k - 1;
        if(czyKrolewska(result)){
            cout << result << "\n";
        }
        else cout << result - 1 << "\n";
    }
    return 0;
}
