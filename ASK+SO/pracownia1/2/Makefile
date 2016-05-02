CC = gcc -g -Wall

2: 2c.o 2s.o
	$(CC) -o 2 2s.o 2c.o
2c.o: 2.c 2.s
	$(CC) -c 2.c -o 2c.o
2s.o: 2.s 
	$(CC) -c 2.s -o 2s.o
clean:
	rm -f 2s.o 2c.o 2 *~


