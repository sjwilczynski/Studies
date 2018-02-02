CC = gcc -g -Wall

4: 4c.o 4s.o
	$(CC) -o 4 4s.o 4c.o
4c.o: 4.c 4.s
	$(CC) -c 4.c -o 4c.o
4s.o: 4.s 
	$(CC) -c 4.s -o 4s.o
clean:
	rm -f 4s.o 4c.o 4 *~


