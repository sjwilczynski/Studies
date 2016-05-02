CC = gcc -g -Wall

1: 1c.o 1s.o
	$(CC) -o 1 1s.o 1c.o
1c.o: 1.c 1.s
	$(CC) -c 1.c -o 1c.o
1s.o: 1.s 
	$(CC) -c 1.s -o 1s.o
clean: 
	rm -f 1 1s.o 1c.o *~



