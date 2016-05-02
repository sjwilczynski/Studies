#include <iostream>
#include <vector>
#include <unordered_map>

inline void makelowercase( std::string& s )
{
    for( char& c : s )
        c = tolower(c);
}

struct case_insensitive
{
    bool operator( ) ( const std::string& s1, const std::string& s2 ) const
    {
        size_t m = s1.length() < s2.length() ? s1.length() : s2. length( );
        for(size_t i = 0;i < m; i++)
        {
            int a = tolower(s1[i]);
            int b = tolower(s2[i]);
            if(a != b) return a < b;
        }
	return s1.length() < s2.length();
    }
};
struct case_insensitive_hash
{
    size_t operator ( ) ( const std::string& s ) const
    {
        std::string ns = s;
        makelowercase(ns);
        std::hash< std::string > str_hash;
        return str_hash(ns);
    }
};

struct case_insensitive_equality
{
    bool operator ( ) ( const std::string& s1, const std::string& s2 ) const
    {
	if(s1.length() != s2.length()) return false;
        size_t m = s1.length() < s2.length() ? s1.length() : s2. length( );
        for(size_t i = 0;i < m; i++)
        {
            int a = tolower(s1[i]);
            int b = tolower(s2[i]);
            if(a != b) return false;
        }
        return true;
    }
};



