Flags = --std=gnu++11 -Wall -Wextra
CPP = g++

server :  server.o functions.o wrapper.o
	$(CPP) $(Flags) -o server  server.o functions.o wrapper.o 
server.o :   server.cpp  functions.h wrapper.h
	$(CPP) -c $(Flags) server.cpp -o server.o
functions.o : functions.cpp functions.h wrapper.h 
	$(CPP) -c $(Flags) functions.cpp -o functions.o 
wrapper.o : wrapper.h wrapper.cpp
	$(CPP) -c $(Flags) wrapper.cpp -o wrapper.o 
clean:
	rm -f *.o *~
distclean:
	rm -f *.o *~ server