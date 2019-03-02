#include<iostream>
#include<vector>


template <typename X> struct polynomial
{
    std::vector<X> v;
    explicit polynomial<X>( std::vector<X> p ) : v {p}
    {}
};
template <typename X> polynomial<X> multiply(const polynomial<X>& p1, const polynomial<X>& p2)
{
        std::vector<X> res;
        std::vector<X> p = p1.v;
        std::vector<X> q = p2.v;
        int n = p.size() + q.size() - 1;
        while(p.size()!=n)p.push_back(0);
        while(q.size()!=n)q.push_back(0);
        res.reserve(n);
        for(int i = 0; i < n; i++)
        {
            X d = 0;
            for(int j = 0; j <= i; j++)d += p[j]*q[i-j];
            res.push_back(d);
        }
        polynomial<X> P = polynomial<X>(res);
        return P;
}
