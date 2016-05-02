class HtmlObject(object):
	def html(self):
		s ='<p>'
		d = self.__dict__
		for i in d:
			s += "nazwa pola: "+ i + ", "
			s += "typ pola: " + "%s, " %(type(d[i]))
			s += "wartosc pola: " "%s\n" %(d[i])
		s += '<\p>'
		return s
class Cos(HtmlObject):
	def __init__(self,name,kolor,numer):
		self.name = name
		self.kolor = kolor
		self.numer = numer
x = Cos("ja","blue",1)
print(x.html())
 	
