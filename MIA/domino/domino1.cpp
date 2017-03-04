#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    int n,a,b,p,k;
    int nieparzyste = 0;
    p = -1;
    k = -1;
    cin >> n;
    vector< int > krawedzie;
    krawedzie.resize(0);
    krawedzie.reserve(2*n+1);
    for(int i = 0; i < n; i++)
    {
        cin >> a >> b;
        krawedzie.push_back(a);
        krawedzie.push_back(b);
    }
    sort(krawedzie.begin(),krawedzie.end());
    int dim = krawedzie.size();
    //for(int i = 0; i < dim; i++) cout << krawedzie[i] << " ";
    //cout << "\n";
    int i = 0;
    while(i < dim-1)
    {
        int deg = 1;
        while( i < dim-1 && krawedzie[i] == krawedzie[i+1])
        {
            deg++;
            i++;
        }
        if(deg % 2 == 1)
        {
            nieparzyste++;
            if(p == -1) p = krawedzie[i];
            else
            {
                if(k == -1) k = krawedzie[i];
            }
        }
        i++;
    }
    if( i == dim-1 && krawedzie[i] != krawedzie[i-1])
    {
        nieparzyste++;
        if(p == -1) p = krawedzie[i];
        else
        {
            if(k == -1) k = krawedzie[i];
        }
    }
    //cout << p << " " << k << "\n";
    bool glupi = true;
    for(int i = 0; i < dim-1; i++)
        if(krawedzie[i]!= krawedzie[i+1]) glupi = false;
    if(!glupi && (nieparzyste > 2 || nieparzyste == 0)) cout << "NIE\n";
    else{
        if(glupi) cout << krawedzie[0] << " " << krawedzie[0];
        else cout << p << " " << k << "\n";
    }
    if(k <= p && p!=-1 && k!=-1) krawedzie[100000000] = 1;
    return 0;
}


