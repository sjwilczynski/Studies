#include "listtest.h"

void listtest::sort1( std::list< std::string > & l)
{
    for( auto q = l. begin( ); q != l. end( ); ++ q )
        for( auto p = l. begin( ); p != q; ++ p )
        {
            if( *p > *q )
            {
                std::string s = *p;
                *p = *q;
                *q = s;
            }
        }
}
void listtest::sort2( std::list< std::string > & l)
{
    for( auto q = l. begin( ); q != l. end( ); ++ q )
        for( auto p = l. begin( ); p != q; ++ p )
        {
            if( *p > *q )
            {
                std::swap(*p,*q);
            }
        }
}
std::ostream& operator << ( std::ostream& stream, const std::list< std::string > & lista)
{
    for( auto& s : lista ) stream << s << " ";
    return stream;
}


