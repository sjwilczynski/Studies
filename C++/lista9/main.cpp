#include <chrono>
#include "functions.h"


bool cmp1 ( const std::string& s1, const std::string& s2 )
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

size_t hash1( const std::string& s )
{
        std::string ns = s;
        makelowercase(ns);
        std::hash< std::string > str_hash;
        return str_hash(ns);   
}
bool equal( const std::string& s1, const std::string& s2 )
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



int main()
{
    std::map< std::string,unsigned int > freq1;
    std::map< std::string,unsigned int > freq2;
    std::map< std::string,unsigned int > freq3;
    std::unordered_map< std::string, unsigned int > f1;
    std::unordered_map< std::string, unsigned int > f2;
    std::unordered_map< std::string, unsigned int > f3;
    std::vector< std::string > v;
    std::vector< std::string > v1;
    std::fstream file("3.html");
    readfile(file,v);
    readfile(file,v1);

    auto t11 = std::chrono::high_resolution_clock::now( );
    freq1 = frequencytable(v);
    auto t12 = std::chrono::high_resolution_clock::now( );
    f1 = hash_frequencytable(v);
    auto t13 = std::chrono::high_resolution_clock::now( );

    readfile(file,v);
    auto t21 = std::chrono::high_resolution_clock::now( );
    freq2 = frequencytable(v);
    auto t22 = std::chrono::high_resolution_clock::now( );
    f3 = hash_frequencytable(v);
    auto t23 = std::chrono::high_resolution_clock::now( );

    readfile(file,v);
    auto t31 = std::chrono::high_resolution_clock::now( );
    freq3 = frequencytable(v);
    auto t32 = std::chrono::high_resolution_clock::now( );
    f3 = hash_frequencytable(v);
    auto t33 = std::chrono::high_resolution_clock::now( );

    std::chrono::duration< double > d1 = ( t12 - t11 );
    std::chrono::duration< double > d2 = ( t22 - t21 );
    std::chrono::duration< double > d3 = ( t32 - t31 );
    std::chrono::duration< double > d4 = ( t13 - t12 );
    std::chrono::duration< double > d5 = ( t23 - t22 );
    std::chrono::duration< double > d6 = ( t33 - t32 );

    std::cout << "for map:\n 1*3.html = " << d1.count() << "\n 2*3.html = " << d2.count() << "\n 3*3.html = " << d3.count() << " seconds\n";
    std::cout << "for unordered_map:\n 1*3.html = " << d4.count() << "\n 2*3.html = " << d5.count() << "\n 3*3.html = " << d6.count() << " seconds\n";
	
auto cmp = [] ( const std::string& s1, const std::string& s2 )
{
    size_t m = s1.length() < s2.length() ? s1.length() : s2. length( );
    for(size_t i = 0;i < m; i++)
    {
        int a = tolower(s1[i]);
        int b = tolower(s2[i]);
        if(a != b) return a < b;
    }
	return s1.length() < s2.length();
};


auto hash = [] ( const std::string& s )
{
        std::string ns = s;
        makelowercase(ns);
        std::hash< std::string > str_hash;
        return str_hash(ns);   
};
auto equal = []( const std::string& s1, const std::string& s2 )
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
};


    auto m1 = nfrequencytable(v1, cmp);
    auto m2 = nhash_frequencytable(v1,hash,equal);
    std::cout << most_frequent(freq1);
    //std::cout << m1;
    //std::cout << m2;
    auto m3 = nfrequencytable < bool (*) ( const std::string& s1, const std::string& s2 ) > (v1,cmp1);
    auto m4 = nhash_frequencytable < size_t (*) (const std::string& s), bool (*) ( const std::string& s1, const std::string& s2 ) > (v1,hash,equal); 
    //std::cout << m3;
    //std::cout << m4;

    file.close();
    return 0;
}

