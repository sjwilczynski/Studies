#include <iostream>

using namespace std;

int main()
{
    int W,H,w,h;
    cin >> W >> H >> w >> h;
    int d1 = W/(2*w);
    int d2 = H/(2*h);
    int r1 = W - d1*2*w;
    int r2 = H - d2*2*h;
    if( r1 >= w ){
        d1++;
    }
    if( r2 >= h ){
        d2++;
    }
    cout << ((long long)d1)*((long long)d2);
    return 0;
}
