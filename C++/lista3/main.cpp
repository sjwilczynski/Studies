#include<cstdio>
#include "stack.h"

int main()
{
    /**
    stack s;
    for(int i=1; i <= 30; i++)
    {
        std::cout << s << "\n";
        s.push(i);
    }
    //std::cout << s << "\n";
    for(int i=1;i<=10;i++)
    {
        std::cout << s.top() << "\n";
        s.pop();
    }
    std::cout << s << "\n";

    s.reset(10);
    std::cout << s << "\n";
    stack s1 = s; // to samo co = stack(s) ???
    stack s2;
    s2 = s1;
    s = s2;
    stack s3 = s1;
    std::cout << s << "\n";
    std::cout << s1 << "\n";
    std::cout << s2 << "\n";
    */

    for( unsigned int i = 0; i < 10; ++ i )
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


    return  0;
}
