#include "rational.h"
#include <cstdlib>


int rational::gcd( int n1, int n2 )
{
    if(n1<0)n1=-n1;
    if(n2<0)n2=-n2;
    if(n1==0 && n2==0)return 1;
    if(n2==0)return n1;
    if(n1==0)return n2;
    return gcd(n2,n1%n2);
}

void rational::normalize( )
{
    int a = gcd(num,denum);
    num = num/a;
    denum = denum/a;
    if(denum==0)throw std::runtime_error ("can't divide by 0");
    if(denum< 0)
    {
        num=-num;
        denum = -denum;
    }
}

rational operator - ( rational r )
{
    int a = r.getnum();
    return rational(-a,r.getdenum());
}

rational operator + ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    int a = x1 * y2 + y1 * x2;
    int b = x2 * y2;
    return rational(a,b);
}

rational operator - ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    int a = x1 * y2 - y1 * x2;
    int b = x2 * y2;
    return rational(a,b);

}

rational operator * ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    int a = x1 * y1;
    int b = x2 * y2;
    return rational(a,b);

}

rational operator / ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    int a = x1 * y2;
    int b = x2 * y1;
    return rational(a,b);
}

bool operator == ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    if(x1 == y1 && x2 == y2)return true;
    return false;

}
bool operator != ( const rational& r1, const rational& r2 )
{
    int x1 = r1.getnum();
    int x2 = r1.getdenum();
    int y1 = r2.getnum();
    int y2 = r2.getdenum();
    if(x1 == y1 && x2 == y2)return false;
    return true;
}

std::ostream& operator << ( std::ostream& stream, const rational& r )
{
    stream << r.getnum() << "/" << r.getdenum();
    return stream;
}



