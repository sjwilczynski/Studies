class Kolekcja
	def initialize(tab)
		@tab = tab
	end
	def length()
		@tab.length
	end
	def get(i)
		if i <= self.length() then @tab[i-1]
		else "Error:Wrong index"
		end
	end
	def swap(i,j)
		if (i <= self.length() && j <= self.length)
		a = @tab[i-1]
		@tab[i-1] = @tab[j-1]
		@tab[j-1] = a
		else "Error:Wrong index"
		end
	end
	def add(i)
		@tab = @tab + [i] 
	end
	def pop(i)
 		@tab.pop(i)
	end
	def put
		print '[',@tab.join(','), ']',"\n" 
	end
end

class Sortowanie
	def sort1(kol)
		n = kol.length()
		for i in (2..n)
			for j in (1..i-1)
			kol.swap(i-j+1,i-j) if kol.get(i-j+1) < kol.get(i-j)
			end
		end
	end
	def sort2(kol)
		n = kol.length()
		if(n>1)
			m = n/2
			k1 = Kolekcja.new([])
			k2 = Kolekcja.new([])
			for i in (1..m)
				k1.add(kol.get(i))
			end
			for i in (m+1..n)
				k2.add(kol.get(i))
			end
			self.sort2(k1)
			self.sort2(k2)
			i1 = 1
			i2 = 1 
			kol.pop(n)
			for i in (1..n)
				if(i2 > k2.length() || (i1 <= k1.length() && k1.get(i1) < k2.get(i2)))
					kol.add(k1.get(i1))
					i1 = i1 + 1
				else
					kol.add(k2.get(i2))
					i2 = i2 + 1 
				end
			end
		end
	
	end
end

k = Kolekcja.new([7,12,13,16,28,31,1,0,3,5,7,6,12,9,2,3,4,56,102,5,14,31,7,8,9,10])
k.add(10)
s = Sortowanie.new()
s.sort2(k)
k.put
