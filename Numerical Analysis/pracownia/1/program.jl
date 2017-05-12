#############################################
# Stanisław Wilczyński
# Analiza numeryczna - pracownia P.1.12
#############################################


#obliczanie wartości wyrazów ciągu - metoda I
y = e-1
for i=1:21
    println(y)
    y = e - i*y
end

#obliczanie wartości wyrazów ciągu - metoda II
y = e/(21)
n = 20
while n > 0
  if n%2 == 0
      println(y)
  end
  y = (e-y)/n
  n = n-1
end





