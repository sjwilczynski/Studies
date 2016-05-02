#include "functions.h"


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
    input.clear();
    input.seekg(0,std::ios_base::beg);
}
template < typename A, typename B > std::ostream& operator << (std::ostream& out, const std::pair< A,B >& p)
{
    out << p.first << " -> " << p.second << "\n";
    return out;
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
template< typename C  > std::ostream& operator << ( std::ostream& out, const std::map< std::string, unsigned int, C > & m )
{
    for(auto& p : m) out << p;
    return out;
}

template< typename A = std::hash< std::string >, typename B = std::equal_to<std::string> >
            std::unordered_map < std::string, unsigned int, A, B >
								hash_frequencytable(const std::vector< std::string >& text)
{
    std::unordered_map< std::string, unsigned int, A, B> counter;
    for(const std::string& s : text)
    {
        ++ counter[s];
    }
    return counter;
}
template< typename A , typename B > std::ostream& operator << ( std::ostream& stream,
        const std::unordered_map< std::string,unsigned int, A, B> & freq )
{
    for(auto& p : freq) stream << p;
    return stream;
}
template< typename A, typename C > std::pair< std::pair< A, unsigned int >, std::pair< A, unsigned int > >
                    most_frequent( const std::map< A, unsigned int, C > & mp )
{
    std::pair<A,unsigned int> max1;
    std::pair<A,unsigned int> max2;
    for(auto& p : mp)
    {
        if(max1.second < p.second) max1 = p;
        else if(max2.second < p.second) max2 = p;
    }
    return std::pair< std::pair< A, unsigned int >, std::pair< A, unsigned int > >(max1,max2);
}

