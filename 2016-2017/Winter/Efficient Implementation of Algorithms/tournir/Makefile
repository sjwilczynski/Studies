Flags = -Wreturn-type -pedantic -Wundef -std=c++11 -O2 -static
CPP = g++

main :  main.o  
	$(CPP) $(Flags) -o main  main.o
main.o :   main.cpp  
	$(CPP) -c $(Flags) main.cpp -o main.o