#include "rational.h"
#include <cstdlib>


int rational::gcd( int n1, int n2 )
{
    if(n1==0 && n2==0)return 1;
    if(n2==0)return abs(n1);
    if(n1==0)return abs(n2);
    return abs(gcd(n2,n1%n2));
}

void rational::normalize( )
{
    int a = gcd(num,denum);
    num = num/a;
    denum = denum/a;
    if(num<0 && denum< 0)
    {
        num=-num;
        denum = -denum;
    }
}

rational operator - ( rational r )
{
    r.num = -r.num;
    return r;
}

rational operator + ( const rational& r1, const rational& r2 )
{
    int a = r1.num * r2.denum + r2.num * r1.denum;
    int b = r1.denum * r2.denum;
    return rational(a,b);
}

rational operator - ( const rational& r1, const rational& r2 )
{
    int a = r1.num * r2.denum - r2.num * r1.denum;
    int b = r1.denum * r2.denum;
    return rational(a,b);

}

rational operator * ( const rational& r1, const rational& r2 )
{
    int a = r1.num * r2.num;
    int b = r1.denum * r2.denum;
    return rational(a,b);

}

rational operator / ( const rational& r1, const rational& r2 )
{
    int a = r1.num * r2.denum;
    int b = r1.denum * r2.num;
    return rational(a,b);


}

bool operator == ( const rational& r1, const rational& r2 )
{
    if(r1.num == r2.num && r1.denum == r2.denum)return true;
    return false;

}
bool operator != ( const rational& r1, const rational& r2 )
{
    if(r1.num == r2.num && r1.denum == r2.denum)return false;
    return true;
}

std::ostream& operator << ( std::ostream& stream, const rational& r )
{
    stream << r.num << "/" << r.denum;
    return stream;
}



