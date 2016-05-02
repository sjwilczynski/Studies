#include "stack.h"


void stack::ensure_capacity( size_t c )
{
    if( current_capacity < c )
    {
// New capacity will be the greater of c and
// 2 * current_capacity.
        if( c < 2 * current_capacity )
            c = 2 * current_capacity;
        double* newtab = new double[ c ];
        for( size_t i = 0; i < current_size; ++ i )
            newtab[i] = tab[i];
        current_capacity = c;
        delete[] tab;
        tab = newtab;
    }
}
void stack::operator = ( const stack& s )
{
    if(tab != s.tab)
    {
        delete [] tab;
        tab = new double [s.current_capacity];
        current_size = s.current_size;
        current_capacity = s.current_capacity;
        for(size_t i=0; i < s.current_size; i++)
        {
            tab[i] = s.tab[i];
        }
    }
    std::cout << "assigning\n";
}
// These are the essential methods.
// Later we will also encounter
// void operator = ( stack&& s ) and
// stack( stack&& s ).
void stack::push( double d )
{
    ensure_capacity(current_size+1);
    tab[current_size++] = d;
    std::cout << d << "\n";
}
// Use ensure_capacity, so that
// pushing is always possible, as
// long as memory is not full.
void stack::pop( )
{
    current_size--;
}
// Remove one element from the stack. It’s OK to write
// code that crashes, as long as you write clearly what are
// your preconditions, so:
// PRECONDITION: The stack is not empty.
void stack::reset( size_t s )
{
    current_size = s;
}
// Pops element until stack has size s.
// PRECONDITION: s <= current_size.
double& stack::top( )
{
    return tab[current_size-1];
}
double stack::top( ) const
{
    return tab[current_size-1];
}
// The second one is used when stack was declared const.
// The first one allows assignment.
// Both have precondition that the stack is non-empty.
std::ostream& operator << ( std::ostream& stream, const stack& s )
{
    if(!s.empty())
    {
        size_t i = s.current_size;
        while(i>0)
        {
            i--;
            stream << s.tab[i] << " ";
        }
    }
    else stream << "stack is empty\n";
    return stream;
}

stack stack::operator + (const stack& s) const
{
    stack nowy = *this;
    size_t sz = s.getsize();
    nowy.ensure_capacity( nowy. current_capacity+s.current_capacity);
    for(size_t i = 0;i < sz;i ++)
    {
        nowy.push(s[sz-i-1]);
    }
    return nowy;
}
/**
stack operator + (const stack& s1, const stack& s2)
{
    size_t sz1 = s1.getsize();
    size_t sz2 = s2.getsize();
    stack s;
    for(size_t i = 0;i < sz1;i ++)
    {
        s.push(s1[sz1-i-1]);
    }
    for(size_t i = 0;i < sz2;i ++)
    {
        s.push(s2[sz2-i-1]);
    }
    return s;
}
*/
void stack::operator += ( double d ) // Same as push( );
{
    push(d);
}
void stack::operator += ( const stack& s )
{
   size_t c = s. current_size;

        for(size_t i = 0;i < c; i ++)
        {
            push(s.tab[i]);
        }
    
}



