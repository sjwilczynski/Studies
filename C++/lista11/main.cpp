
#include "varchain.h"
#include "polynomial.h"
#include "bigint.h"
#include "rational.h"


template< typename N >
polynomial< N > exptaylor( unsigned int n )
{
   varchain v;

   N fact = 1;

   polynomial< N > result;
   for( unsigned int i = 0; i < n; ++ i )
   {
      result[v] += fact;
      v = v * powvar( "x" );
      fact = fact / (i+1);
   }

   return result;
}

int main()
{
   std::cout << "hello\n";


   polynomial< rational > pol;

   int N = 50;

   pol[ { } ] = 1;
   pol[ { "x" } ] = rational( 1, N );
   polynomial <int> p1;
   p1[ {} ] = 1;
   p1[ {"x"} ] = 1;
   polynomial<int> p = p1;
   for(int i = 0; i < 4; i++)p = p*p1;
   std::cout << p << "\n";

   polynomial <int> p2;
   varchain v = { powvar("x",2),powvar("y",1),powvar("z",3)};
   p2[ {} ] = 1;
   p2[ v ] = 1;
   p = p2;
   for(int i = 0; i < 3; i++)p = p*p2;
   std::cout << p << "\n";

   polynomial <int> p3;
   varchain w = { powvar("x",1),powvar("y",1)};
   p3[ {} ] = 3;
   p3[ w ] = 1;
   p = p3;
   for(int i = 0; i < 5; i++)p = p*p3;
   std::cout << p << "\n";

   /*
   polynomial<rational> p = pol+pol;
   p = p+p;
   std::cout << p << p*p << "\n";
*/

   polynomial< rational > res = 1;

   for( int i = 0; i < N; ++ i )
      res = res * pol;
/*
   std::cout << "rsult = " << res << "\n";

   std::cout << " taylor expansion = " << exptaylor<rational>(20) << "\n\n\n";

   std::cout << "difference = " ;
   std::cout << res - exptaylor<rational> ( 50 ) << "\n";
*/   return 0;
}


