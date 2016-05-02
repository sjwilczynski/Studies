#include <fstream>
#include <iostream>
#include <map>
#include <vector>
#include <unordered_map>
#include "case_insensitive.h"

void readfile( std::istream& input, std::vector< std::string > & vect )
{
    if( !input. good( ))
        return;

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
    if( lastword. size( ))
    {
        vect.push_back(lastword);
    }
}

template< typename C = std::less< std::string > > std::map< std::string, unsigned int, C >
                frequencytable( const std::vector< std::string > & text )
{
    std::map< std::string, unsigned int, C > counter;
    for(const std::string& s : text)
    {
        ++ counter[s];
    }
    return counter;
}
template< typename C > std::ostream& operator << ( std::ostream& out, const std::map< std::string, unsigned int, C > & m )
{
    for(auto& p : m) out << p.first << " -> " << p.second << "\n";
    return out;
}

//template< typename A = std::hash, typename B = std::equal_to > std::unordered_map < std::string, unsigned int, A, B > 
								//hash_frequencytable(const std::vector< std::string >& text)
std::unordered_map< std::string, unsigned int, case_insensitive_hash, case_insensitive_equality>
                    hash_frequencytable(const std::vector< std::string >& text)
{
    std::unordered_map< std::string, unsigned int, case_insensitive_hash, case_insensitive_equality> counter;
    for(const std::string& s : text)
    {
        ++ counter[s];
    }
    return counter;
}
std::ostream& operator << ( std::ostream& stream, const std::unordered_map< std::string,
                            unsigned int, case_insensitive_hash, case_insensitive_equality> & freq )
{
    for(auto& p : freq) stream << p.first << " -> " << p.second << "\n";
    return stream;
}

int main()
{
    std::map< std::string, unsigned int, case_insensitive > freq;
    std::vector< std::string > v;
    std::fstream file("short.html");
    readfile(file,v);
    std::cout << frequencytable<case_insensitive>(v) << "\n\n\n";
    /*case_insensitive c;
    std::cout << c( "a", "A" ) << " " << c( "a","b" ) << " " << c( "A", "b" ) << "\n";

    case_insensitive_hash h;
    std::cout << h( "xxx" ) << " " << h( "XXX" ) << "\n";
    std::cout << h( "Abc" ) << " " << h( "abC" ) << "\n";

    case_insensitive_equality e;
    std::cout << e( "xxx", "XXX" ) << "\n";
    */std::cout << hash_frequencytable(v);/*
    std::cout << hash_frequencytable( std::vector<std::string> ( { "AA", "aa", "bb", "BB" } ) );
    //std::string s = "abc";
    //std::cout << 'A' - 'a';
*/
    std::string a = "aa";
    std::string b = "aaa";
	std::cout << a[2] << "\n";
	std::cout << (a[2] > b[2]) << "\n";
    return 0;
}
