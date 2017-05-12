#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>
#include <vector>

using namespace std;

typedef pair < int, int > pii;
pii punkty[500004];

inline double dist( pii& a, pii& b)
{
    double x = a.first - b.first;
    double y = a.second - b.second;
    double res = x*x + y*y;
    return sqrt( res );
}

std::ostream& operator << ( std::ostream& stream, const pii& a)
{
    stream << a.first << " " << a.second << "\n";
    return stream;
}

struct cztery{
    pii r1;
    pii r2;
    pii r3;
    double d;
    cztery(pii& re1, pii& re2, pii& re3, double& d1):
        r1{re1},r2{re2},r3{re3},d{d1}{}
    cztery(){}
    friend std::ostream& operator << ( std::ostream& stream, const cztery& c )
    {
        stream << c.r1 << c.r2 << c.r3;
        return stream;
    }
};

inline double param( pii& a, pii& b, pii& c)
{
    return dist(a,b) + dist(a,c) + dist(b,c);
}

inline int distx(pii& a, double l)
{
    int res = a.first - l;
    if(res < 0) return -res;
    return res;
}
inline int distx(pii& a, pii& b)
{
    int res = a.first - b.first;
    if(res < 0) return -res;
    return res;
}
inline int disty(pii& a, pii& b)
{
    int res = a.second - b.second;
    if(res < 0) return -res;
    return res;
}
inline double distx(pii& a, pair<double,double>& b)
{
    double res = a.first - b.first;
    if(res < 0) return -res;
    return res;
}

bool cmp(pii a, pii b)
{
    if(a.second != b.second)return a.second < b.second;
    else return a.first < b.first;
}
inline bool comp(pii& a, pair< double,double >& b)
{
    if(a.first != b.first) return a.first < b.first;
    else return a.second < b.second;
}

cztery shortest(pii* pox, int size, vector<pii>& poy)
{
    cztery res;
    res.d = 4000000000.0;
    if(size < 10)
    {
        for(int i = 0; i < size; i++)
            for(int j=i+1; j < size; j++)
                for(int k = j+1; k < size; k++)
                {
                        double ob = param(pox[i],pox[j],pox[k]);
                        if(ob < res.d)
                        {
                                //res = cztery(pox[i],pox[j],pox[k],ob);
                                res.r1 = pox[i];
                                res.r2 = pox[j];
                                res.r3 = pox[k];
                                res.d = ob;
                        }
                }
        return res;
    }
    int size2 = (size + 1)/2;

    pair< double, double > pivot2( (pox[size2].first + pox[size2-1].first)/2.0, (pox[size2].second + pox[size2-1].second)/2.0 );
    vector< pii > temp;
    temp.reserve(size2);

    for( int i = 0; i < size; i++ )
        if( comp( poy[i],pivot2 ) )
            temp.push_back(poy[i]);
    res = shortest( pox, size2, temp );
    temp.clear();

    for(int i=0; i < size; i++)
        if( !comp( poy[i],pivot2 ) )
            temp.push_back(poy[i]);
    cztery res2 = shortest( pox+size2, size - size2, temp );
    if(res2.d < res.d)
        res = res2;
    temp.clear();

    double d = res.d/2.0;

    for( int i = 0; i < size; i++ )
        if( distx( poy[i],pivot2 ) < d )
            temp.push_back( poy[i] );

    size = temp.size();
    for( int i = 0; i < size; i ++ )
    {
        int m = min( i+16, size );
        for( int  j = i+1; j < m; j++ )
        {
            if(disty(temp[i],temp[j]) >= d) break;
            if(dist(temp[i],temp[j]) >= d)continue;
            for( int k = j+1; k < m; k++ )
            {
                    double ob = param( temp[i], temp[j], temp[k] );
                    if(ob < res.d)
                    {
                            //res = cztery(temp[i],temp[j],temp[k],ob); dolna wersja szybsza
                            res.r1 = temp[i];
                            res.r2 = temp[j];
                            res.r3 = temp[k];
                            res.d = ob;
                    }
            }
        }
    }
    return res;
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    int n,x,y;
    cin >> n;
    for(int i = 0; i < n; i++)
    {
        cin >> x >> y;
        pii p = pii(x,y);
        punkty[i] = p;
    }
    vector< pii > poY( punkty,punkty+n );
    sort( punkty,punkty+n );
    sort( poY.begin(),poY.end(),cmp );
    cztery res = shortest(punkty,n,poY);
    //cout << res.d << "\n";
    cout << res;
    return 0;
}
