class ObiektyDodawalne:
	def __init__(self,name):
		self.name = name
	def __add__(self,obj):
		if(isinstance(obj,ObiektyDodawalne)):
			ds = self.__dict__
			do = obj.__dict__
			for i in do:
				if(i in ds.keys()):
					print("UWAGA! atrybut '%s' już istnieje" %(i))
				ds[i] = do[i]
		else:
			print("Nie można dodać")
	def __repr__(self):
		for i in self.__dict__:
			print(i+" "+ self.__dict__[i])
class BUUU:
	def __init__(self,name):
		self.name = name
class Obiekt1(ObiektyDodawalne):
	def __init__(self,name,number,color):
		self.name = name
		self.number = number
		self.color = color
class Obiekt2(ObiektyDodawalne):
	def __init__(self,name,price,quality):
		self.name = name
		self.price = price
		self.quality = quality
a = Obiekt1("okno","2","blue")
b = Obiekt2("ja","bezcenny","super")
buuu = BUUU("buuu")
#(a+b).__repr__()
a+buuu
a.__repr__()
a+b
a.__repr__()

