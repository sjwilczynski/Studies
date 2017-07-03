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
	return lt #ladne sito

def pierwsze_skladana(n):
	lp = [x for x in range(2,n+1) if len([y for y in range(2,x/2+1) if x%y == 0])==0]
	return lp

def pierwsze_funkcyjna(n):
	
	return filter(lambda x: len([y for y in range(2,x/2+1) if x%y == 0]) == 0 , range(2,n+1))

start1 = time.clock()
print pierwsze_skladana(100)
stop1 = time.clock()
print pierwsze_funkcyjna(100)
stop2 = time.clock()

print( stop1 - start1, stop2-stop1)
