#include <fstream>
#include <iostream>
#include <chrono>

#include "listtest.h"
#include "vectortest.h"

void readfile( std::istream& input, std::vector< std::string > & vect )
{
    if( !input. good( ))
        return;  // It was a short pleasure.

    std::string lastword;
    int c = input. get( );
    while( input. good( ))
    {
        while((isspace(c) || ispunct(c)) && input.good()) c = input.get();
        while(!isspace(c) && !ispunct(c) && input.good())
        {
            lastword += (char) c;
            c = input.get();
        }
        vect.push_back(lastword);
        lastword = "";

    }

    // A file should not end without a newline, but we need
    // to do something when it happens.

    if( lastword. size( ))
    {
        vect.push_back(lastword);
    }
}

// Anonymous namespace. This means that this function
// is guaranteed to be unique in the program, so that
// we don't have to worry about makelowercase functions defined
// in other files.

namespace
{
// 'inline' is ignored by the compiler, but it makes me feel better.

inline void makelowercase( std::string& s )
{
    for( char& c : s )
        c = tolower(c);
}
}

void makelowercase( std::vector< std::string > & vect )
{
    for( auto& s : vect )
        makelowercase(s);
}


std::list< std::string >
makelist( const std::vector< std::string > & vect )
{
    std::list< std::string> result;

    for( const auto& s : vect )
        result. push_back(s);
    return result;

}

int main( int argc, char* argv [] )
{
    std::vector< std::string > vect;
    // This use of int is one of those ugly remainders of C--.

    for( int i = 1; i != argc; ++ i )
    {
        std::ifstream input { argv[i] };
        if( !input )
        {
            std::cerr << "could not open input file " << argv[i] << "\n";
        }
        else
        {
            readfile( input, vect );
        }
    }

    makelowercase( vect );

    //std::cout << vect << "\n";
    std::vector< std::string > vect1 = vect;
    std::vector< std::string > vect2 = vect;
    std::vector< std::string > vect3 = vect;
    std::vector< std::string > vect4 = vect;

    std::list< std::string > lst1 = makelist( vect );
    std::list< std::string > lst2 = makelist( vect );

    auto t1 = std::chrono::high_resolution_clock::now( );
    vectortest::sort1(vect1);
    auto t2 = std::chrono::high_resolution_clock::now( );
    auto t3 = std::chrono::high_resolution_clock::now( );
    vectortest::sort2(vect2);
    auto t4 = std::chrono::high_resolution_clock::now( );
    auto t5 = std::chrono::high_resolution_clock::now( );
    vectortest::sort3(vect3);
    auto t6 = std::chrono::high_resolution_clock::now( );
    auto t7 = std::chrono::high_resolution_clock::now( );
    vectortest::sort4(vect4);
    auto t8 = std::chrono::high_resolution_clock::now( );
    auto t9 = std::chrono::high_resolution_clock::now( );
    listtest::sort1( lst1 );
    auto t10 = std::chrono::high_resolution_clock::now( );
    auto t11 = std::chrono::high_resolution_clock::now( );
    listtest::sort2( lst2 );
    auto t12 = std::chrono::high_resolution_clock::now( );

    std::chrono::duration< double > d1 = ( t2 - t1 );
    std::chrono::duration< double > d2 = ( t4 - t3 );
    std::chrono::duration< double > d3 = ( t6 - t5 );
    std::chrono::duration< double > d4 = ( t8 - t7 );
    std::chrono::duration< double > d5 = ( t10 - t9 );
    std::chrono::duration< double > d6 = ( t12 - t11 );
    //std::cout << vect << "\n";

    std::cout << "vector sorting(1) took " << d1. count( ) << " seconds\n";
    std::cout << "vector sorting(2) took " << d2. count( ) << " seconds\n";
    std::cout << "vector sorting(3) took " << d3. count( ) << " seconds\n";
    std::cout << "vector sorting(4) took " << d4. count( ) << " seconds\n";
    std::cout << "list sorting(1) took " << d5. count( ) << " seconds\n";
    std::cout << "list sorting(2) took " << d6. count( ) << " seconds\n";
    //std::cout << vect1;
    return 0;
}


