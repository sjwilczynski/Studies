class Fixnum
	def prime?
		for i in (2..self)
		return true if i*i>self
		return false if self%i == 0
		end
	end
	def ack(y) 
		return y+1 if self == 0
		return (self-1).ack(1) if y == 0
		return (self-1).ack(self.ack(y-1))
	end
	def doskonala
		d = 0
		for i in (1..self/2+1)
			d += i if self%i == 0
		end
	return true if d == self  
	return false
	end
	def slownie
	@tablica = ['zero','jeden','dwa','trzy','cztery','piec','szesc','siedem','osiem','dziewiec']
	s = self
	res = []
	while s != 0
		res.push(@tablica[s%10])
		s = s/10
	end
	res = res.reverse
	r = ""
	for i in (0..res.size-1)
	r += "#{res[i]} " 
	end
	return r
	end
end

print 4.prime? ,"\n"
print 3.ack(2) ,"\n"
print 495.doskonala,"\n"
puts 1234061.slownie
puts 11/10
