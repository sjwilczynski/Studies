Flags = --std=gnu++11 -Wall -Wextra
CPP = g++

router :  router.o functions.o wrapper.o
	$(CPP) $(Flags) -o router  router.o functions.o wrapper.o 
router.o :   router.cpp  functions.h wrapper.h
	$(CPP) -c $(Flags) router.cpp -o router.o
functions.o : functions.cpp functions.h wrapper.h 
	$(CPP) -c $(Flags) functions.cpp -o functions.o 
wrapper.o : wrapper.h wrapper.cpp
	$(CPP) -c $(Flags) wrapper.cpp -o wrapper.o 
clean:
	rm -f *.o *~
distclean:
	rm -f *.o *~ router