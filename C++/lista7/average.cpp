#include "average.h"
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
