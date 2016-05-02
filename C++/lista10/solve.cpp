#include "move.h"
#include "fifteen.h"
#include <unordered_map>
#include <queue>

template < typename A, typename B > std::ostream& operator << (std::ostream& out, const std::pair< A,B >& p)
{
    out << p.first << " -> " << p.second << "\n";
    return out;
}

using leveltable =
std::unordered_map< fifteen, size_t, size_t(*) ( const fifteen& ),
                    bool(*) ( const fifteen& , const fifteen& ) > ;


bool further( const fifteen& f1, const fifteen& f2 )
{
   return f1. distance( ) > f2. distance( );
}

size_t hash( const fifteen& f )
{
   return f. hashvalue( );
}

bool equals( const fifteen& f1, const fifteen& f2 )
{
   return f1. equals( f2 );
}

// state is the state that we are trying to solve.

#if 1

leveltable
solve( const fifteen& start )
{
   leveltable levels{ 0, hash, equals }; //ten dziwny konstruktor

   std::priority_queue< fifteen, std::vector< fifteen >,
                        bool(*) ( const fifteen& , const fifteen& ) >
   unchecked{ further };

   unchecked. push( start );
   levels[ start ] = 0;

   // We have start unexplored, at its level is zero.

   std::vector<move> moves = { move::up, move::down, move::left, move::right };
      // All possible moves in a vector.

   // As long as there is a state whose neighbours are unexplored,
   // we explore them:

   while( unchecked. size( ))
   {
      fifteen best = unchecked. top( );
      size_t level = levels [ best ];
         // The best state is the state that is closest to the
         // solution. level is the number of moves that was required
         // to reach it.

      std::cout << "best = " << best << "\n";
      std::cout << "distance = " << best. distance( ) << "\n";

      if( best. issolved( ))
         return levels;

      unchecked. pop( );

      for( auto m : moves )
      {
         fifteen next = best;
         try
         {
            next. makemove( m );
            auto p = levels. find( next );
            if( p == levels. end( ) || level + 1 < p -> second )
            {
               // We did not reach next before, or we reached it
               // in more steps.

               levels[ next ] = level + 1;
               unchecked. push( next );
            }
         }
         catch( illegalmove& m ) { /* catch and do nothing */ }
      }
   }
   return levels;  // In move we trust.
}

#endif

std::list< move > findpath( const leveltable& levels,
                            fifteen f, size_t level )
{

   std::vector<move> moves = { move::up, move::down, move::left, move::right };
      // All possible moves in a vector.
   std::list< move > path;
   if(level > 0)
   {
        for( auto m : moves )
        {
            fifteen next = f;
            try
            {
                next. makemove( m );
                auto p = levels. find( next );
                if( p != levels. end( ) && level - 1 == p -> second )
                {
                    path = findpath(levels, next, level - 1);
                    path.push_front( -m );
                    return path;
                }
            }
            catch( illegalmove& m ) { /* catch and do nothing */ }
        }
   }
   return path;
}





int main(  )
{
   fifteen f{ { 1, 3, 4, 12 }, { 5, 2, 7, 11 }, { 9, 6, 14, 10 }, { 13, 15, 0, 8 } } ;
   fifteen f2{ { 0, 3, 4, 12 }, { 5, 2, 7, 11 }, { 9, 6, 14, 10 }, { 13, 15, 8, 1 } };
   fifteen f3{ {0,12,9,13}, {15,11,10,14} , {7,8,6,2}, {4,3,5,1}};
   fifteen f1;

/*
   std::cout << f;
   std::cout << "\n" << f.distance() << "\n";
   std::cout << f.issolved();
   std::cout << f1.issolved();
   std::cout << "\n" << f.hashvalue() << "\n" << f1.hashvalue() << "\n" << f2.hashvalue() << "\n";
   std::cout << f.equals(f1);
   std::cout << fifteen::solvedposition(12);
*/




   auto dist = solve(f3);
/*
   for( const auto& p : dist )
   {
      std::cout << "---------------------------\n";
      std::cout << p;
   }
*/

   auto path = findpath( dist, fifteen( ), dist[ fifteen( ) ] );
   for( move m : path )
      std::cout << m << "\n";
    std::cout << dist[ fifteen( ) ];

   return 0;
}


