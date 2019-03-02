#include <iostream>
#include <cstdlib>
#include <typeinfo>
// with noexcept only move constructor(0,812) is used, without it, move constructir(511,301) is used when resizing the vector
struct xx
{
    int val;
    static unsigned int copied;
    static unsigned int moved;
    explicit xx( int val ) : val {val}
    { }
    xx( xx&& x ) noexcept
        : val { x. val }
    {
        std::cout << "move constr " << val << "\n";
        ++ moved;
    }
    xx( const xx& x )
        : val { x. val }
    {
        std::cout << "copy constr " << val << "\n";
        ++ copied;
    }
};
unsigned int xx::copied = 0;
unsigned int xx::moved = 0;
