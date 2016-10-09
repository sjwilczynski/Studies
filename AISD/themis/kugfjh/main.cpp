#include <iostream>
#include <climits>

using namespace std;

int addds(int x, int y)
{
    long long x1 = (long long) x;
    long long y1 = (long long) y;
    if(x1 + y1 <= INT_MIN) return INT_MIN;
    if(x1 + y1 >= INT_MAX) return INT_MAX;
    else return (int)(x+y);
}

int main()
{
    return 0;
}
