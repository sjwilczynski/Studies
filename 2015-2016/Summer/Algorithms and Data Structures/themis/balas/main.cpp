#include <iostream>
#include "costam.h"

static int x;

void f()
{
    static int x = 0;
    x++;
    std::cout << x << std::endl;
    return;
}
void g()
{
    static int x = 1;
    x++;
    std::cout << x << std::endl;
    return;
}


int main()
{
    f();
    f();
    f();
    g();
    std::cout << x << std::endl;
    f();

    return 0;
}
