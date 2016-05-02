CC = gcc -g -Wall

5: 5c.o 5s.o
	$(CC) -o 5 5s.o 5c.o
5c.o: 5.c 5.s
	$(CC) -c 5.c -o 5c.o
5s.o: 5.s 
	$(CC) -c 5.s -o 5s.o
clean:
	rm -f 5s.o 5c.o 5 *~



