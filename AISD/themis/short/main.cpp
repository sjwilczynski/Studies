#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>
#include <vector>

using namespace std;

typedef pair < int, int > pii;
typedef pair < pair < int, int >, int > ppiii;
pii punkty[100001];
long dlugosc;
double mymin(double a, double b)
{
    if(a < b) return a;
    else return b;
}

double dist( pii& a, pii& b)
{
    double x = (double)(a.first - b.first);
    double y = (double)(a.second - b.second);
    double res = x*x + y*y;
    return sqrt( res );
}
int distx(pii& a, pii& b)
{
    int res = a.first - b.first;
    if(res < 0) res = -res;
    return res;
}
bool cmp1( pii a, pii b )
{
    return a.first < b.first;
}
bool cmp2(pii a, pii b)
{
    return a.second < b.second;
}
inline double shortest(int i, int j)
{
    if( j - i == 1) return dist(punkty[i], punkty[j]);
    if( j - i == 2)
    {
        double d1 = dist( punkty[i], punkty[i+1] );
        double d2 = dist( punkty[i], punkty[i+2] );
        double d3 = dist( punkty[i+2], punkty[i+1] );
        return mymin(d1, mymin( d2,d3 ) );
    }
    int k = (i+j)/2;
    double d1 = shortest(i,k);
    double d2 = shortest(k+1,j);
    double d = mymin(d1,d2);
    vector< pii > vec;
    vec.resize(0);
    double l = (punkty[k+1].first + punkty[k].first)/2.0;
    for(int ind = j ; ind > k; ind --)
    {
        if( punkty[ind].first - l <= d ) vec.push_back(punkty[ind]);
    }
    for(int ind = k; ind >= i; ind --)
    {
        if( l - punkty[ind].first <= d ) vec.push_back(punkty[ind]);
    }
    sort(vec.begin(),vec.end(),cmp2);
    for(unsigned int ind1 = 0; ind1 < vec.size(); ind1 ++)
    {
        for(unsigned int ind2 = 1; (ind2 <= 7) && (ind1 + ind2 < vec.size()); ind2++ )
        {
            double res = dist( vec[ind1], vec[ind1+ind2] );
            if(res < d) d = res;
        }
    }
    return d;
}

double shortest2(pii* pox, int size, vector<pii>& poy)
{
    double d = dist(pox[0],pox[1]);
    if(size < 5)
    {
        for(int i = 0; i < size; i++)
        {
            for(int j=i+1; j < size; j++)
            {
                double res = dist(pox[i],pox[j]);
                if(res < d) d= res;
            }
        }
        return d;
    }
    pii pivot = pox[ size/2 ];
    vector< pii > temp;
    temp.reserve(size/2);
    for( int i = 0; i < size; i++ )
    {
        if( poy[i] < pivot )
            temp.push_back(poy[i]);
    }
    d = shortest2( pox, size/2, temp );
    temp.clear();
    temp.reserve( size/2+1 );
    for(int i=0; i < size; i++)
    {
        if( poy[i] > pivot )
            temp.push_back(poy[i]);
    }
    d = min( d, shortest2( pox+size/2+1, size - size/2 - 1, temp ) );
    temp.clear();
    for(int i = 0; i < size; i++)
    {
        if(distx(poy[i],pivot) < d)
            temp.push_back(poy[i]);
    }
    size = temp.size();
    for(int ind1 = 0; ind1 < size; ind1 ++)
    {
        for(int ind2 = ind1+1; ind2 < min(ind1+8,size); ind2++ )
        {
            double res = dist( temp[ind1], temp[ind2] );
            if(res < d) d = res;
        }
    }
    return d;
}


int main()
{
	dlugosc = 0;
    int n,x,y;
    scanf("%d",&n);
    for(int i = 0; i < n; i++)
    {
        scanf("%d %d",&x,&y);
        pii p = pii(x,y);
        punkty[i] = p;
    }

    sort(punkty,punkty+n,cmp1);
    vector< pii > poY(punkty,punkty+n);
    sort(poY.begin(),poY.end());
    double res = shortest2(punkty,n,poY); //hahahaha jak było cmp2 to wrzucał niektóre punkty nie tam gdzie trzeba ;(((((
    printf("%.5lf\n",res);

    return 0;
}
