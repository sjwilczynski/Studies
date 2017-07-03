#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;
long long A;
long long a[200001];
long long liczby[200001];
vector< vector < int > > grzadki;
vector < int > v;
long long P = 9999991;
long long N;
long long wartosc;
long long zakres = 50000001;

bool check_size()
{
    int suma = 0;
    for(int i = 0; i <= N; i++)
    {
        int x = grzadki[i].size();
        suma += x*x;
        if(suma > 2*N-1) return false;
    }
    return true;
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    srand( time(NULL) );
    cin >> N;
    for(int i = 0; i < N; i++)
    {
        cin >> wartosc;
        liczby[i] = wartosc;
    }
    while(true)
    {
        grzadki.resize(0);
        grzadki.resize(N+1);
        A = rand() % zakres;
        for(int i = 0; i < N; i++)
        {
            long long x = ( ( A*liczby[i] ) % P ) % (N+1);
            grzadki[x].push_back(i);
        }
        if( check_size() )
            break;
    }
    cout << N+1 << "\n";
    for(int i = 0; i < N+1; i++)
    {
        int x = grzadki[i].size();
        cout << x*x+1  << " ";
    }
    cout << "\n" << A << "\n";
    for(int i = 0; i <= N; i++)
    {
        bool dalej = true;
        while(dalej)
        {
            dalej = false;
            v.resize(0);
            long long x = grzadki[i].size();
            a[i] = rand() % zakres;
            v.resize(x*x+1,-1);
            for(int j = 0; j < x; j++)
            {
                long long y =  ( ( a[i] * liczby[ grzadki[i][j] ] ) % P ) % (x*x+1);
                if(v[y] != -1)
                {
                    dalej = true;
                    break;
                }
                else
                    v[y] = grzadki[i][j];
            }
        }
    }
    for(int i = 0; i <= N; i++)
        cout << a[i] << " ";
    cout << "\n";
    return 0;
}
