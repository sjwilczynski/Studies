#include "tree.h"


int main()
{
    for(int i=0; i<100; i++)
    {
        tree t1( std::string( "a" ));
        tree t2( std::string( "b" ));
        tree t3 = tree( std::string( "f" ), { t1, t2 } );
        //std::cout << "ble\n";
        std::vector< tree > arguments = { t1, t2, t3 };
        tree t5 = tree( "F", std::move( arguments ));
        std::cout << t5 << "\n";
        std::cout << subst(t5,"a",t2) << "\n";
        //std::cout << "ble\n";

        t2 = t3;
        tree t4 = t2;
        std::cout << t4 << "\n";
        t4[1] = t1;
        t4.functor() = "hello";
        std::cout << t4 << "\n";
        std::cout << t2 << "\n";
        t2 = std::move(t3);
    }
}



