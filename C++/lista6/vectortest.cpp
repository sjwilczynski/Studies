#include "vectortest.h"

void vectortest::sort1( std::vector< std::string > & v )
{
    for( size_t j = 0; j < v. size( ); ++ j )
        for( size_t i = 0; i < j; ++ i )
        {
            if( v[i] > v[j] )
            {
                std::swap( v[i], v[j] );
            }
        }
}

void vectortest::sort2( std::vector< std::string > & v )
{
    for( size_t j = 0; j < v. size( ); ++ j )
        for( size_t i = 0; i < j; ++ i )
        {
            if( v[i] > v[j] )
            {
                std::string s = v[i];
                v[i] = v[j];
                v[j] = s;
            }
        }
}
void vectortest::sort3( std::vector< std::string > & v )
{
    for( auto q = v. begin( ); q != v. end( ); ++ q )
        for( auto p = v. begin( ); p != q; ++ p )
        {
            if( *p > *q )
            {
                std::swap( *p, *q );
            }
        }
}
void vectortest::sort4( std::vector< std::string > & v )
{
    for( auto q = v. begin( ); q != v. end( ); ++ q )
        for( auto p = v. begin( ); p != q; ++ p )
        {
            if( *p > *q )
            {
                std::string s = *p;
                *p = *q;
                *q = s;
            }
        }
}

std::ostream& operator << ( std::ostream& stream, const std::vector< std::string > & vect )
{
    for( auto& s : vect ) stream << s << " ";
    return stream;
}

