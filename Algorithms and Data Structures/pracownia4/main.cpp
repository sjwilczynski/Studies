#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>
#include <vector>

using namespace std;

struct piec{
    int x;
    int y;
    int c;
    int odjx;
    int odjy;
    piec(int& x1, int& y1, int& c1):
        x{x1},y{y1},c{c1},odjx{0},odjy{0}{}
    piec():
        x{0},y{0},c{0},odjx{0},odjy{0}{}
    friend std::ostream& operator << ( std::ostream& stream, const piec& p )
    {
        stream << p.x << " " << p.y << " " << p.c << " " << p.odjx << " " << p.odjy << "\n";
        return stream;
    }
};

pair< int, vector<piec> > wynik[2][10002];
piec marchewki[10002];


bool cmp1(piec& p1, piec& p2)
{
    if(p1.x != p2.x) return p1.x < p2.x;
    else return p1.y < p2.y;
}
bool cmp2(piec& p1, piec& p2)
{
    if(p1.y != p2.y) return p1.y < p2.y;
    else return p1.x < p2.x;
}
void poprawx(vector<piec>& t,int k)
{
    int i = 0;
    int prev;
    if( t[i].x != 1 )
    {
        prev = t[i].x;
        t[i].odjx = prev - 1;
        t[i].x = 1;
        i++;
    }
    else
        while(i < k && t[i].x == 1)
        {
            i++;
            prev = 1;
            //cout << "!!!!!" << p << t[0];
        }
    while(i < k)
    {
        if(t[i].x == prev)
        {
            t[i].odjx = t[i-1].odjx;
            t[i].x = t[i-1].x;
        }
        else
        {
            t[i].odjx = t[i].x - (t[i-1].x + 1);
            prev = t[i].x;
            t[i].x = t[i-1].x + 1;
        }
        i++;
    }
}
void poprawy(vector<piec>& t,int k)
{
    int i = 0;
    int prev;
    if( t[i].y != 1 )
    {
        prev = t[i].y;
        t[i].odjy = prev - 1;
        t[i].y = 1;
        i++;
    }
    else
        while(i < k && t[i].y == 1)
        {
            i++;
            prev = 1;
            //cout << "!!!!!" << p << t[0];
        }
    while(i < k)
    {
        if(t[i].y == prev)
        {
            t[i].odjy = t[i-1].odjy;
            t[i].y = t[i-1].y;
        }
        else
        {
            t[i].odjy = t[i].y - (t[i-1].y + 1);
            prev = t[i].y;
            t[i].y = t[i-1].y + 1;
        }
        i++;
    }
}

int main()
{
    int n,m,k,x,y,c;
    vector< piec > tab;
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cin >> n >> m;
    cin >> k;
    tab.reserve(k);
    for(int i = 0; i < k; i++)
    {
        cin >> x >> y >> c;
        tab.push_back(piec(x,y,c));
    }
    sort(tab.begin(),tab.end(),cmp1);
    poprawx(tab,k);
    sort(tab.begin(),tab.end(),cmp2);
    poprawy(tab,k);
    //for(int i = 0; i < k; i++)
        //cout << tab[i];
    int ind = 0;
    for(int i=1; i <=k; i++)
    {
        for(int j = 1; j <=k; j++)marchewki[j] = piec();
        while(ind < k && tab[ind].y == i)
        {
            piec& p = tab[ind];
            marchewki[p.x] = p;
            ind ++;
        }
        for(int j = 1; j <= k; j++)
        {
            //wynik[i%2][j] = max(wynik[i%2][j-1],wynik[(i-1)%2][j]) + marchewki[j];
            pair< int , vector<piec> >& p = wynik[i%2][j];
            pair< int , vector<piec> > p1 = wynik[i%2][j-1];
            pair< int , vector<piec> > p2 = wynik[(i-1)%2][j];
            p = p1.first > p2.first ? p1 : p2;
            if(marchewki[j].c > 0)
            {
                p.first += marchewki[j].c;
                p.second.push_back(marchewki[j]);
            }
        }
    }

    pair< int , vector<piec> >& p = wynik[k%2][k];
    cout << p.first << "\n";
    int s = p.second.size();
    cout << s << "\n";
    for(int i = 0; i < s; i++)
        cout << p.second[i].x + p.second[i].odjx << " " << p.second[i].y + p.second[i].odjy << "\n";
    return 0;
}
