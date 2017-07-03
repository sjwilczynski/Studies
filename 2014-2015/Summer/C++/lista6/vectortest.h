
#ifndef VECTORTEST_INCLUDED
#define VECTORTEST_INCLUDED  1

#include <vector>
#include <string>
#include <fstream>
#include <iostream>

namespace vectortest
{
   void sort1( std::vector< std::string > & v ); // 0.39558 1.515 3.219
   void sort2( std::vector< std::string > & v ); // 0.52
   void sort3( std::vector< std::string > & v ); // 0.55
   void sort4( std::vector< std::string > & v ); // 0.618
}

std::ostream&  operator << ( std::ostream& , const std::vector< std::string > & );

#endif

