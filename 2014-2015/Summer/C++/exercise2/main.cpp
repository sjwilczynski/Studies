
#include "rational.h"
#include "matrix.h"

int main( int argc, char* argv [ ] )
{
   rational r1( 6, 2 );
   rational r2( 4, 3 );
   rational r3( 5,6 );
   rational r4( 1, 2 );
   //std::cout << r1 << "\n";

   matrix m1 = { { 3, 3 }, { 4, 6 } };
   //std::cout << m1 << "\n";

   matrix m2 = { { 5,4 }, { 6, rational(1,2) } };
   //std::cout << m2 << "\n";

   matrix m3 = { { 4,5}, { -1, 2 }};

   //std::cout << m1. determinant( ) << "\n";
   //std::cout << m1. adjugate( ) << "\n";
   std::cout << m1. inverse( ) * m1 << "\n";

   matrix A = {{rational(1,2),rational(-2,7)}, {rational(1,3),rational(2,8)}};
   matrix B = {{rational(-1,3),rational(2,5)}, {rational(2,7),rational(-1,7)}};
   std::cout << A << "\n";
   std::cout << B << "\n";
   std::cout << A*B << "\n";
   std::cout << A.inverse() << "\n";
   std::cout << (A*B)*m1 << "\n" << A*(B*m1) << "\n";

   vector v = {rational(4),rational(5,2)};
   std::cout << A(B(v)) << "\n" << (A*B)(v) << "\n";

   if(A.determinant()*B.determinant()==(A*B).determinant())std::cout << "\nDeterminant commutes over multiplication\n";
   matrix I = {{1,0},{0,1}};

}

