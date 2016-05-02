#include<iostream>

using namespace std;

struct para{
    int x;
    int y;
    para(const para& p):
        x{p.x},y{p.y} {
        cout << "copy" << endl;
        }
    para(pair<int,int>&& p) noexcept:
        x{move(p.first)},y{move(p.second)} {
        cout << "rvalue" << endl;
        }
    para(int x, int y):
        x{x},y{y} {
        cout << "int "<< x << " " << y << endl;
        }
    void operator =( const para& p)
    {
        x = p.x;
        y = p.y;
        cout << "assign" << endl;
    }
    int getx()
    {
        return x;
    }

};

class liczba{
    int x;
    int y;
    liczba(int x, int y):
        x{x},y{y} {}
};
int ds(unsigned int a)
{
    return 1;
}
int ds(float n)
{
    return 2;
}
class K1{
public :
    int n;
    int fun(){return 0;}
};
class K2 : public K1
{
    public:
    int fun(){return 1;}
};
void fun(K1* k)
{
    cout << k -> fun() << endl;
}
class foo
{
     static int i;
};
int foo::i = 5;


class A
{
    int n;
    public:
    virtual int method()
    {
        return n;
    }
};
class B : A
{
    public:
    int method()
    {
        return 1;
    }
};

int main()
{
    //cout << foo::i <<endl;
    A* a = new A();
    B* b = new B();
    A *x[2];
    x[0] = a;
    x[1] = (A*)b;
    cout << x[1]->method() << endl << "----------------------------" << endl;

    cout << a->method() << " " << b->method() << endl;

    K1* k1 = new K1();
    K2* k2 = new K2();
    fun(k2);
    cout << "--------------------------------" << endl;
    int n = 1;
    //for("";"";"")
        //cout << n;
    cout << ds(static_cast<float>(n)) << endl << "---------------------------------------------" << endl;
    int zmienna[] = {10};
    cout << zmienna[0] << endl;
    para p(1,2);
    para p1 = para(1,2);
    p1 = para(2,3);
    para p3(p);
    para p4(pair<int,int>(5,4));
    para p2 = p;
    p1 = p;
    cout << p.x << endl;
    p.getx();
    cout << p.x << endl;
    return 0;
}
