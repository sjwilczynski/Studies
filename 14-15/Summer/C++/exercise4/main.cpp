#include<cstdio>
#include "stack.h"

int main()
{
for( int p = 0;p < 1000; p++)
{
    stack costam({1.1,2,3});
    std::cout << "\n" << costam << "\n";
    stack s;
    for(int i=1; i <= 25; i++)
    {
        std::cout << s << "\n";
        s.push(i);
    }
    for(int i=1;i<=10;i++)
    {
        std::cout << s.top() << "\n";
        s.pop();
    }
    std::cout << s << "\n";

    s.reset(10);
    stack s1 = s; // to samo co = stack(s) ???
    std::cout << s1 << "\n";
    std::cout << s1 << "\n";
    stack s3 = s1;
    stack s4 = s1;
    s4.reset(5);
    s1 = s4;
    s4.reset(3);
    s1.reset(2);
    s4 = s1;
    s1.pop();
    s1 = s4;
    s1 = s1;
    std::cout << s3 << "\n";
    std::cout << s4 << "\n";
    std::cout << s3 + s4 << "\n";
    std::cout << s << "\n";
    for(int i=0;i <= 1; i++)std::cout << s4[i] << " ";
    //std::cout << s4[11];

    std::cout << s1 << "\n" << s3 << "\n";
    stack s6 = s3;
    s3 += s6;
    s3 += s3;
    std::cout << s3 << "\n";

    stack t({1,2,3,4,5,6,7});
    stack d({8,9,15,12,0});
    std::cout << t << "\n" << d << "\n";
    std::cout << t+d << "\n";
    std::cout << d+t << "\n";
    std::cout << t[0] << " " << t[1] << " " << t[2] << "\n";
    stack s7 = t;
    stack s8;
    s8 = t;
    std::cout << t << "\n";
    s.pop();
    s.pop();
    t += s7;
    t += 8;
    t += 9;
    std::cout << t << "\n";

/**

    for( unsigned int i = 0; i < 1000000; ++ i )
    {
        stack s1;
        s1. push(45);
        s1. push(45);
        s1. push(46);
        stack s2 = s1;
// j is not size_t, because multiplying size_t with itself is
// unnatural:
        for( unsigned int j = 0; j < 20; ++ j )
            s2. push( j * j );
        s1 = s2;
// Assignment.
        s1 = s1;
// Self assignemnt should always be checked.
//#if 0
// Won’t compile. In order to get it compiled, remove const:
        stack& sconst = s1;
        sconst. top( ) = 20;
        sconst. push(15);
//#endif
    }
*/
}
    return  0;
}
