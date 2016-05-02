import timeit
class MyError(Exception):
	def __init__(self,value):
		self.value = value

def silnia(a,b):
	if (b==1):
		try:
			raise MyError(a)
		except MyError as e:
			e.value
	else:
		try:
			return b*silnia(b*a,b-1)
		except TypeError:
			pass
def ble1():
	return silnia(1,300)
def ble2():
	return sil(300)
def sil(b):
	if(b==1):
		 return b
	else:
		return b*sil(b-1)
print(timeit.timeit(ble1,number = 10))
print(timeit.timeit(ble2,number = 10))
def fib(a):
	if(a == 0):
		return 1
	if(a == 1):
		return 1
	return fib(a-1)+fib(a-2)
def fib(a):
	if(a == 0):
		return 1
	if(a == 1):
		return 1
	return fib(a-1)+fib(a-2) 


#t.timeit(1)
#silnia(1,100)


