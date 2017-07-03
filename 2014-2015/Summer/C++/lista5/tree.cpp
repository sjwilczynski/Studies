#include "tree.h"


void tree::ensure_not_shared()
{
    if(this->pntr->refcnt == 1)return;
    else
    {
        //this->pntr->refcnt--;
        //*this = tree(*this);
        *this = tree(pntr->f, pntr->subtrees); //patrzac na to co wyswietla najpierw construuje a potem rvalue assignment(usuwa to wymienione?) wiec sam zmiejsza refcnt????
    }
}
std::ostream& operator << ( std::ostream& stream, const tree& t)
{
    stream <<  t.functor() << " ";
    for(size_t i = 0; i < t.nrsubtrees(); i ++)
    {
        stream << t[i] << " ";
    }
    return stream;
}
tree subst( const tree& t,const std::string& var, const tree& val )
{
    if(t.nrsubtrees() == 0 && t.functor() == var)
    {
        std::cout << "cos\n";
        return val;
    }
    std::vector <tree> ns;
    for(size_t i = 0; i < t.nrsubtrees(); i++)
    {
        ns.push_back(subst(t[i],var,val));
    }
    return tree(t.functor(),ns);
}

