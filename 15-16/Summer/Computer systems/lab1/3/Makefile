CC = gcc -g -Wall

3: 3c.o 3s.o
	$(CC) -o 3 3s.o 3c.o
3c.o: 3.c 3.s
	$(CC) -c 3.c -o 3c.o
3s.o: 3.s 
	$(CC) -c 3.s -o 3s.o
clean:
	rm -f 3s.o 3c.o 3 *~


