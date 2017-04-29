Flags = --std=gnu++11 -Wall -Wextra
CPP = g++

traceroute :  traceroute.o functions.o wrapper.o
	$(CPP) $(Flags) -o traceroute  traceroute.o functions.o wrapper.o 
traceroute.o :   traceroute.cpp  functions.h wrapper.h
	$(CPP) -c $(Flags) traceroute.cpp -o traceroute.o
functions.o : functions.cpp functions.h wrapper.h 
	$(CPP) -c $(Flags) functions.cpp -o functions.o 
wrapper.o : wrapper.h wrapper.cpp
	$(CPP) -c $(Flags) wrapper.cpp -o wrapper.o 
clean:
	rm -f *.o *~
distclean:
	rm -f *.o *~ traceroute