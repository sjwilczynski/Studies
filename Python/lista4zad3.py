
f = open('plik.txt','r')
#for i in range(1,30):
#	x = f.read(1)
#	print x

class FileIter():
	def __init__(self,f):
		self.licznik = 0
		self.f = f
		self.f.seek(0,2)
		self.end = f.tell()
		self.f.seek(0,0)
	def __next__(self):
		s = ""
		self.f.seek(self.licznik,0)
		if(self.licznik >= self.end): raise StopIteration
		while(self.licznik < self.end):
			s += self.f.read(1)
			self.licznik += 1
			if( s[-1] == '.'):
				x = self.f.read(1)
				while(x.isspace()):
					s += x
					self.licznik += 1
					x = self.f.read(1) 
				return s 
class TextCol():
	def __init__(self,f):
		self.f = f
	def __iter__(self):
		return FileIter(f)				
def korekta(zdanie):
	if(zdanie == None): return ""
	if(zdanie[0] in 'abcdefghijklmnopqrstuvwxyz' ):
		zdanie = zdanie.capitalize()
	if(zdanie[-1] != '.') :
		zdanie = zdanie.strip()
	return zdanie

x = FileIter(f)
y = TextCol(f)
it = map(korekta,y)
print(next(it))
print( next(it))
print( next(it))
print( next(it))
print(next(it))
