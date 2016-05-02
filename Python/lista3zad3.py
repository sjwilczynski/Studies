import time

def sito(n):
	lt = []
	for i in range(0,n+1,1):
		lt.append(1)
	lt[0] = 0
	lt[1] = 0
	for i in range(2,n+1,1):
		if(i*i > n): break
		if(lt[i] == 1):
			for j in range(2*i, n+1, i):
				lt[j] = 0
	return lt

def rozklad_skladana(n):
	L = [(x, [p for p in range(1,n+1) if n%(x**p) == 0][-1]) for x in range(2,n+1) if len([y for y in range(2,x) if x%y == 0]) == 0 and n%x == 0  ]
	return L

def rozklad_funkcyjna(n):
	L1 = filter(lambda x: len([y for y in range(2,x) if x%y == 0])==0 and n%x == 0, range(2,n+1) )
	L = map( lambda x: (x,[p for p in range(1,n+1) if n%(x**p) == 0][-1]), L1)
 	return L



start1 = time.clock()
print rozklad_skladana(756)
stop1 = time.clock()
print rozklad_funkcyjna(756)
stop2 = time.clock()

print( stop1 - start1, stop2-stop1)
