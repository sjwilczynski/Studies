Flags = -Wall -Wundef -pedantic -pedantic-errors -std=c++11
CPP = g++


main :   main.o 
	$(CPP) $(Flags) -o main   main.o   

main.o : main.cpp  ci.h functions.h
	$(CPP) -c $(Flags) main.cpp -o  main.o
