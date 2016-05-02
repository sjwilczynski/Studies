
#ifndef VARCHAIN_INCLUDED
#define VARCHAIN_INCLUDED 1

#include <iostream>
#include <vector>
#include <algorithm>


// powvar is a silly name. There doesn't seem to exist a standard name.
// A powvar is a variable to some integer power, for example x^10
// or y^{-5}.

struct powvar
{
   std::string v;
   int n;

   powvar( const std::string& v, int n = 1 )
      : v{v}, n{n}
   { }
   struct cmp
   {
      bool operator() (const powvar& i,const powvar& j)
      {
         if(i.v != j.v) return i.v < j.v;
         else return i.n < j.n;
      }
   };
};


std::ostream& operator << ( std::ostream &, const powvar & );

// A varchain is a sequence of powvars, sorted by the variable.
// For example x^{10}.y^{-5}, z^{3}.
// They are always normalized:
// Identical variables are merged, and zero powers are removed:
// x^{2}.x^{3}.y^{3}.y^{-2}.y^{-1} ==> x^{5}.

struct varchain
{

   std::vector< powvar > repr;

   varchain( ) { }

   varchain( const std::string& s, int n = 1 )
      : repr( { powvar(s,1) } )
   { normalize( ); }

   varchain( const powvar& p )
      : repr( { p } )
   { normalize( ); }

   varchain( std::initializer_list< powvar > repr )
      : repr{ repr }
   { normalize( ); }

   varchain( const std::vector< powvar > & repr )
      : repr{ repr }
   { normalize( ); }

   varchain( std::vector< powvar > && repr )
      : repr{ std::move( repr ) }
   { normalize( ); }


   bool isunit( ) const { return repr. size( ) == 0; }
   size_t size( ) const { return repr. size( ); }
   powvar operator [] ( size_t i ) const { return repr[i]; }
   powvar& operator [] ( size_t i ) { return repr[i]; }


   int power( ) const;

   static int compare( const varchain& c1, const varchain& c2 )
   {
        auto p = c1.repr.begin();
        auto q = c2.repr.begin();
        while( p != c1.repr.end() && q != c2.repr.end() )
        {
            if(p->v != q->v)return p->v < q->v;
            if(p->n != q->n)return p->n < q->n;
            p++;q++;
        }
        if(q == c2.repr.end())return 0;
        return 1;
   }

   // Contrary to Java, structs defined inside other structs
   // have no connection with the other struct.
   // The only difference is that you must write varchain::cmp instead
   // of cmp.

   struct cmp
   {
      bool operator() ( const varchain& c1, const varchain& c2 )
      {
         return compare( c1, c2 ); //there was return ... == -1. Why??
      }
   };

   void normalize( )
   {
        std::sort(repr.begin(),repr.end(),powvar::cmp());
        for(auto p = repr.begin()+1; p != repr.end(); p++ )
        {
            if( p->v == (p-1)->v )
            {
                p->n += (p-1)->n;
                repr.erase(p-1);
                p--;
            }
            if( p->n == 0 ) repr.erase(p);
        }
   }
      // 1. Sort the representation by variable.
      // 2. Merge powvars with identical variable.
      // 3. Remove powvars with n == 0.
};


varchain operator * ( varchain c1, const varchain& c2 );

std::ostream& operator << ( std::ostream&, const varchain&  );

#endif

