#include "fifteen.h"

using position = std::pair< size_t, size_t > ;

std::ostream& operator << ( std::ostream& stream, const fifteen& f )
{
    stream << "|" << std::setfill('#') << std::setw(12) << "|\n";
    for( unsigned int i = 0; i < f.dimension; ++ i )
    {
        stream << "|";
        for( unsigned int j = 0; j < f.dimension; ++ j)
        {
            if(j != f.dimension - 1)stream << f.table[i][j] << std::setfill('#') << std::setw(3);
            else stream << f.table[i][j] << "|\n";
        }
        stream << "|" << std::setfill('#') << std::setw(12) << "|\n";
    }
    return stream;
}

size_t fifteen::distance( ) const
{
    size_t dist = 0;
    for( size_t i = 0; i < dimension; ++ i )
        for( size_t j = 0; j < dimension; ++ j )
            {
                position p = solvedposition( table[i][j] );
                dist += distance( position(i,j), p);
            }
    return dist;
}
      // Estimated distance from solution. Use Manhattan distance,
      // see the wikipedia article.

bool fifteen::issolved( ) const
{
    for( size_t i = 0; i < dimension; ++ i )
        for( size_t j = 0; j < dimension; ++ j )
            if( solvedposition( table[i][j] ) != position(i,j) ) return false;

    return true;
}
      // True if the puzzle is in solved state.

size_t fifteen::hashvalue( ) const
{
    size_t hash = 0;
    size_t d = dimension * dimension;
    for( size_t i = 0; i < dimension; ++ i )
    {
        for( size_t j = 0; j < dimension; ++ j )
            hash += table[i][j];
        hash *= d;
    }
    return hash;
}
      // Construct a hash value on the state.

bool fifteen::equals( const fifteen& other ) const
{
    for( size_t i = 0; i < dimension; ++ i )
        for( size_t j = 0; j < dimension; ++ j )
            if( table[i][j] != other.table[i][j] ) return false;

    return true;
}
// True if we are equal to other.
void fifteen::makemove( move m )
{
    switch(m)
    {
        case move::up:
                if( open_i == 0 )
                {
                    illegalmove i(m);
                    std::cout << i;
                }
                else
                {
                    std::swap(table[open_i][open_j], table[open_i-1][open_j]);
                    open_i --;
                }
                break;
        case move::left:
                if( open_j == 0 )
                {
                    illegalmove i(m);
                    std::cout << i;
                }
                else
                {
                    std::swap(table[open_i][open_j], table[open_i][open_j-1]);
                    open_j --;
                }
                break;
        case move::right:
                if( open_j == dimension - 1 )
                {
                    illegalmove i(m);
                    //std::cout << i;
                }
                else
                {
                    std::swap(table[open_i][open_j], table[open_i][open_j+1]);
                    open_j ++;
                }
                break;
        case move::down:
                if( open_i == dimension - 1 )
                {
                    illegalmove i(m);
                    std::cout << i;
                }
                else
                {
                    std::swap(table[open_i][open_j], table[open_i+1][open_j]);
                    open_i ++;
                }
                break;
   }
}
    // This method tries to make a move m. If it fails then
      // it throws illegalmove( move );
      // Note that in C++, you don't have to declare possible exceptions.
      // A move is illegal if it would move the open place out of the
      // board.




