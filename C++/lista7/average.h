#include <iostream>
#include <vector>
#include "rational.h"
// when i wanted to create average.cpp file the programme would not compile. why??
// the error was that there is no definition for function std::ostream& operator << (std::ostream& out, const std::vector<double> &v)

/*
double average(const std::vector<double> &v)
{
    double result = 0;
    for(auto &d : v) result += d;
    return result/v.size();
}

double average(const std::vector<double> &v)
{
    double result = 0;
    for( auto q = v. begin( ); q != v. end( ); ++ q )
    {
        result += *q;
    }
    return result/v.size();
}
*/
template< typename X > std::ostream& operator << (std::ostream& out, const std::vector<X>& v)
{
    out << "{";
    for( auto p = v. begin( ); p != v. end( ); ++ p )
    {
        if( p != v. begin( ))
            out << ",";
        out << " " << *p;
    }
    out << " }";
    return out;
}

template< typename X > X average(const std::vector<X>& v)
{
    X result = 0;
    for(auto &x : v) result += x;
    return result/v.size();
}
std::vector<double> multiply(const std::vector<double>& p1, const std::vector<double>& p2)
{
    std::vector<double> v;
    int n = p1.size() + p2.size() - 1;
    v.reserve(n);
    for(int i = 0; i < n; i++)
    {
        double d = 0;
        for(int j = 0; j <= i; j++)d += p1[j]*p2[i-j];
        v.push_back(d);
    }
    return v;
}
