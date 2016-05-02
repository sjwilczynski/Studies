#Stanisław Wilczyński Pracownia z analizy numerycznej P.2.18

# inicjujemy najważniejsze zmienne w programie:
# n - liczba punktów, ekstrema - ekstrema n-1 wielomianu Czebyszewa, zera - pierwiastki n-tego wielomianu Czebyszewa
# rowno - wezly rownoodległe, a p1,p2,p3 to odpowiednio dobrane wagi
n = 10
ekstrema = Array(Float64,n)
zera = Array(Float64,n)
rowno = Array(Float64,n)
p1 = Array(Float64,n)
p2 = Array(Float64,n)
p3 = Array(Float64,n)
for i = 1:n
  if i == 1 || i == n
    p1[i] = 0.5
  else
    p1[i] = 1.0
  end
  p2[i] = 1.0
  p3[i] = 1.0
  ekstrema[i] = cos((i-1)*pi/(n-1))
  zera[i] = cos((2*i-1)*pi/(2*n))
  rowno[i] = -1.0 + (i-1)*2/(n-1)
end

#funkcja oblicza dyskretny iloczyn skalarny wielomianów w1 i w2 z waga p na zbiorze punktów ar
#wielomiany reprezentujemy jako tablicę gdzie w[i] to współczynnik przy x^(i-1)
function skalar(ar::Array, w1::Array, w2::Array, p::Array)
  res = 0.0
  for i = 1:length(ar)
    res += wielomian(w1,ar[i])*wielomian(w2,ar[i])*p[i]
  end
  return res
end

#funkcja obbliczająca wartość wielomianu w punkcie za pomocą schematu hornera
function wielomian(ar::Array, x::Float64)
  res = 0.0
  for i = 1:length(ar)
    res = res*x + ar[n-i+1]
  end
  return res
end

#pomożenie wielomianu przez x
function razyx(ar::Array)
  n = length(ar)
  for i = 0:n-2
    ar[n-i] = ar[n-i-1]
  end
  ar[1] = 0
  return ar
end

#funkcja ortogonalizująca wykorzystująca regułę trójczłonową
function ortoRT(n,p,punkty)
  #wielomiany ortogonalne przechowujemy w macierzy nxn tak że kolumny tej macierzy są naszymi wielomianami
  baza = eye(n)
  baza = zero(baza)
  baza[1] = 1.0
  bazap = razyx(baza[:,1])
  baza[n+1] = -skalar(punkty,bazap,baza[:,1],p)/skalar(punkty,baza[:,1],baza[:,1],p)
  baza[n+2] = 1.0
  for i = 3:n
    bazap = razyx(baza[:,i-1])
    i1 = skalar(punkty,bazap,baza[:,i-1],p)
    i2 = skalar(punkty,baza[:,i-1],baza[:,i-1],p)
    i3 = skalar(punkty,baza[:,i-2],baza[:,i-2],p)
    c = i1/i2
    d = i2/i3
    for j = 1:n
        baza[:,i] = bazap - c*baza[:,i-1] - d*baza[:,i-2]
    end
  end
  return baza
end

#funkcja ortogonalizująca za pomocą metody Grama-Schmidta
function ortoGS(n,p,punkty)
  baza = eye(n)
  nbaza = zero(eye(n))
  nbaza[:,1] = baza[:,1]
  for i = 2:n
        fi = baza[:,i]
        nbaza[:,i] = fi
        for j = 1:i-1
          gj = nbaza[:,j]
          nbaza[:,i] -= (skalar(punkty,fi,gj,p)/skalar(punkty,gj,gj,p))*gj
        end
  end
  return nbaza
end


#wykonujemy ortogonalizację dla trzech zbiorów punktów, każdą na 2 sposoby i badamy błąd sumaryczny(jak daleko naszym wielomianom do ortogonalnych)
bazaGS1 = ortoGS(n,p1,ekstrema)
bazaRT1 = ortoRT(n,p1,ekstrema)
bazaGS2 = ortoGS(n,p2,zera)
bazaRT2 = ortoRT(n,p2,zera)
bazaGS3 = ortoGS(n,p3,rowno)
bazaRT3 = ortoRT(n,p3,rowno)

bladGS1, bladRT1 = 0.0,0.0
bladGS2, bladRT2 = 0.0,0.0
bladGS3, bladRT3 = 0.0,0.0
for i = 2:n
  for j in 1:i-1
    bladGS1 += abs(skalar(ekstrema,bazaGS1[:,i],bazaGS1[:,j],p1))
    bladRT1 += abs(skalar(ekstrema,bazaRT1[:,i],bazaRT1[:,j],p1))
    bladGS2 += abs(skalar(zera,bazaGS2[:,i],bazaGS2[:,j],p2))
    bladRT2 += abs(skalar(zera,bazaRT2[:,i],bazaRT2[:,j],p2))
    bladGS3 += abs(skalar(rowno,bazaGS3[:,i],bazaGS3[:,j],p3))
    bladRT3 += abs(skalar(rowno,bazaRT3[:,i],bazaRT3[:,j],p3))
    end
end
bladRT1 = bladRT1 * 2.0/(n*(n-1))
bladRT2 = bladRT2 * 2.0/(n*(n-1))
bladRT3 = bladRT3 * 2.0/(n*(n-1))
bladGS1 = bladGS1 * 2.0/(n*(n-1))
bladGS2 = bladGS2 * 2.0/(n*(n-1))
bladGS3 = bladGS3 * 2.0/(n*(n-1))
println(bladRT1)
println(bladGS1)
println(bladRT2)
println(bladGS2)
println(bladRT3)
println(bladGS3)

#rysowanie wykresu dziesiątych znalezionych wielomianów z 9-tego wielomianu Czebyszewa
using Gadfly

f1 = x -> 256*(1*bazaRT1[9*n+1] + x*bazaRT1[9*n+2]+ x^2*bazaRT1[9*n+3] + x^3*bazaRT1[9*n+4] + (x^4)*bazaRT1[9*n+5] + x^5*bazaRT1[9*n+6] + x^6*bazaRT1[9*n+7] + x^7*bazaRT1[9*n+8] + x^8*bazaRT1[9*n+9] + x^9*bazaRT1[9*n+10])
f2 = x -> 256*(1*bazaRT2[9*n+1] + x*bazaRT2[9*n+2]+ x^2*bazaRT2[9*n+3] + x^3*bazaRT2[9*n+4] + (x^4)*bazaRT2[9*n+5] + x^5*bazaRT2[9*n+6] + x^6*bazaRT2[9*n+7] + x^7*bazaRT2[9*n+8] + x^8*bazaRT2[9*n+9] + x^9*bazaRT2[9*n+10])
f4 = x -> 256*(1*bazaGS2[9*n+1] + x*bazaGS2[9*n+2]+ x^2*bazaGS2[9*n+3] + x^3*bazaGS2[9*n+4] + (x^4)*bazaGS2[9*n+5] + x^5*bazaGS2[9*n+6] + x^6*bazaGS2[9*n+7] + x^7*bazaGS2[9*n+8] + x^8*bazaGS2[9*n+9] + x^9*bazaGS2[9*n+10])
f3 = x -> 256*x^9 - 576x^7 + 432*x^5 - 120*x^3 + 9*x
f5 = x -> 256*(1*bazaGS1[9*n+1] + x*bazaGS1[9*n+2]+ x^2*bazaGS1[9*n+3] + x^3*bazaGS1[9*n+4] + (x^4)*bazaGS1[9*n+5] + x^5*bazaGS1[9*n+6] + x^6*bazaGS1[9*n+7] + x^7*bazaGS1[9*n+8] + x^8*bazaGS1[9*n+9] + x^9*bazaGS1[9*n+10])
plot([f1,f2,f3,f4,f5],-1,1)

