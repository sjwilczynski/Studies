CC = gcc -g -Wall

8: 8c.o 8s.o
	$(CC) -o 8 8s.o 8c.o
8c.o: 8.c 8.s
	$(CC) -c 8.c -o 8c.o
8s.o: 8.s 
	$(CC) -c 8.s -o 8s.o
clean:
	rm -f 8s.o 8c.o 8 *~



