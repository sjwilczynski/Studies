#include "xx.h"
#include <vector>
#include <list>
#include "average.h"
#include "polynomial.h"

using namespace std;

int main()
{
    std::vector<xx> v;
    for(int i = 0; i <= 300; i++)v.push_back(xx(i));
    std::cout << xx::copied << " " << xx::moved << "\n";
    for(int i = 0; i <= 300; i++)v.pop_back();
    std::cout << v.capacity(); // it is not resized
    std::vector<xx> nv;
    nv.reserve(512); //now it is (0,301)
    int a = xx::copied;
    int b = xx::moved;
    for(int i = 0; i <= 300; i++)nv.push_back(xx(i));
    std::cout << xx::copied - a << " " << xx::moved - b << "\n";
    a = xx::copied;
    b = xx::moved;

    std::list<xx> l;
    xx x = xx(5);
    for(int i = 0; i <= 300; i++)l.push_back(x);
    std::cout << xx::copied - a << " " << xx::moved - b << "\n";

    std::vector<double> v1;
    v1.push_back(10.12312);
    v1.push_back(1.432);
    v1.push_back(9.6759);
    std::cout << v1 <<"\n";
    std::cout << average(v1) << "\n";

    std::vector<float> v2;
    v2.push_back(10.1863);
    v2.push_back(8.152);
    v2.push_back(9.354);
    std::cout << v2 <<"\n";
    std::cout << average(v2) << "\n";

    std::vector<rational> v3;
    v3.push_back(rational(5,6));
    v3.push_back(rational(2,3));
    v3.push_back(rational(4,12));
    std::cout << v3 <<"\n";
    std::cout << average(v3) << "\n";

    std::vector< std::vector<double> > p;
    for(double i = 1; i <= 9; i++)
    {
        std::vector<double> v;
        v.push_back(i/10.0);
        v.push_back(1);
        p.push_back(v);
    }
    for(int i=1;i < 9; i++)p[0] = multiply(p[0],p[i]);
    std::cout << p[0] << "\n";

    std::vector<rational> x1 = {rational(1,2),1};
    std::vector<rational> x2 = {rational(1,3),1};
    std::vector<rational> x3 = {rational(1,4),1};
    polynomial<rational> p1 = polynomial<rational>(x1);
    polynomial<rational> p2 = polynomial<rational>(x2);
    polynomial<rational> p3 = polynomial<rational>(x3);
    std::cout << multiply(multiply(p1,p2),p3).v << "\n";
    std::cout << multiply(p1,p1).v << "\n";

    return 0;
}
