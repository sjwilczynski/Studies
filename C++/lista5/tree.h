
#ifndef TREE_INCLUDED
#define TREE_INCLUDED  1


#include <iostream>
#include <vector>
#include <string>
#include <stdexcept>


class tree
{
// struct trnode should be invisible to the user of tree. This can be
// obtained by making it a private number of tree.
// In this exercise, we leave it like this, because it is simpler.
// In real code, trnode should be defined inside tree.
private :

struct trnode
{
   std::string f;
   std::vector< tree > subtrees;

   size_t refcnt;
      // The reference counter: Counts how often this trnode is referred to.

   trnode( const std::string& f, const std::vector< tree > & subtrees,
           size_t refcnt )
      : f{f},
        subtrees{ subtrees },
        refcnt{ refcnt }
   { }

   trnode( const std::string& f, std::vector< tree > && subtrees,
           size_t refcnt )
      : f{f},
        subtrees{ std::move( subtrees )},
        refcnt{ refcnt }
   { }

};



   trnode* pntr;

public:
   tree( const std::string& f )
      : pntr( new trnode( f, { }, 1 ))
   {
        std::cout << "constructing from string\n";
   }

   tree( const std::string& f, const std::vector< tree > & subtrees )
      : pntr( new trnode( f, subtrees, 1 ))
   {
        std::cout << "constructing\n";
   }

   tree( const std::string& f, std::vector< tree > && subtrees )
      : pntr( new trnode( f, std::move( subtrees ), 1 ))
   {
        std::cout << "constructing rvalue\n";
   }


   tree( const tree& t )
      : pntr(t.pntr)
      {
            pntr->refcnt++;
            //std::cout << "cokolwiek\n";
      }
      // There is no need to write tree( tree&& t ),
      // because we cannot improve.

   void operator = ( tree&& t )
   {
        std::swap( pntr, t. pntr );
        std::cout << "Rvalue assingment\n";
   }
   void operator = ( const tree& t )
   {
        *this = tree(t);
        std::cout << "assingment\n";
   }

   const std::string& functor( ) const
   {
        return pntr->f;
   }
   std::string& functor( )
   {
        ensure_not_shared();
        return pntr->f;
   }

   const tree& operator [ ] ( size_t i ) const
   {
        if(i >= pntr->subtrees.size()) throw std::runtime_error("wrong index\n") ;
        else return pntr->subtrees[i];
   }
   tree& operator [ ] ( size_t i )
   {
        //std::cout << pntr->refcnt << " sprawdzenie1\n";
        ensure_not_shared();
        //std::cout << pntr->refcnt << " sprawdzenie2\n";
        if(i >= pntr->subtrees.size()) throw std::runtime_error("wrong index\n") ;
        else return pntr->subtrees[i];
   }
   size_t nrsubtrees( ) const
   {
        return pntr->subtrees.size();
   }

   ~tree( )
   {
        if(pntr->refcnt>1)
        {
            pntr->refcnt--;
            //std::cout << pntr->refcnt + 1 << " decreasing " << this->pntr->f << "\n";
        }
        else
        {
            //std::cout << this->pntr->f << " hahahahah ";
            delete pntr;
            std::cout << "destroying\n";
        }
   }

private:
//public:
   // Delete public, when the thing is tested:

   void ensure_not_shared( );
};

std::ostream& operator << ( std::ostream& stream, const tree& t);
tree subst( const tree& t,const std::string& var, const tree& val );
   // Doesn't need to be friend, because it uses only functor( ),
   // nrsubtrees( ) and [ ].

#endif

