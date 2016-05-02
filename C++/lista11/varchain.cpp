
#include "varchain.h"
#include <algorithm>


std::ostream& operator << ( std::ostream& out, const powvar & p )
{
   if( p.n == 0 )
   {
      out << "1"; // Should not happen, but we still have to print something.
      return out;
   }

   out << p.v;
   if( p.n == 1 )
      return out;

   if( p.n > 0 )
      out << "^" << p.n;
   else
      out << "^{" << p.n << "}";
   return out;
}


std::ostream& operator << ( std::ostream& out, const varchain& c )
{
   if( c. isunit( ))
   {
      out << "1";
      return out;
   }

   for( auto p = c. repr. begin( ); p != c. repr. end( ); ++ p )
   {
      if( p != c. repr. begin( ))
         out << ".";
      out << *p;
   }

   return out;
}

int varchain::power( ) const
{
   int p = 0;
   for( auto pv : repr )
      p += pv. n;
   return p;      //do i need this function??
}

varchain operator * ( varchain c1, const varchain& c2 )
{
    if(c2.isunit())return c1;
    for(auto p = c2.repr.begin(); p != c2.repr.end(); p++)
    {
        c1.repr.push_back(*p);
    }
    c1.normalize();
    return c1;
}


