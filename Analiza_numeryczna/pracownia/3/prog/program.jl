#Stanisław Wilczyński Pracownia z analizy numerycznej P.3.3

#generuje zera k-tego wielomianu Czebyszewa
function generuj_zera(k)
  zera = Array(Float64,k)
  for i = 1:k
      zera[i] = cos((2*i-1)*pi/(2*k))
  end
  return zera
end

#generuje ekstrema k-1 wielomianu Czebyszewa
function generuj_ekstrema(k)
  ekstrema = Array(Float64,k)
  for i = 1:k
    ekstrema[i] = cos((i-1)*pi/(k-1))
  end
  return ekstrema
end

#funckja obliczająca wewnętrzne sumy we wzorze na I_n
function wsp_In(f::Function)
  wsp = Array(Float64,n+1)
  for i = 1:n+1
    wsp[i] = skalarf(z,f,czeb[:,i])
  end
  wsp[1] = wsp[1]/2.0
  return wsp
end

#funckja obliczająca wewnętrzne sumy we wzorze na J_n,tzn. wewnętrzne kombinacje liniowe wielomianów Czebyszewa
function wsp_Jn(f::Function)
  ar = Array(Float64,n+1)
  for i = 1:n+1
    ar[i] = f(e[i])
  end
  ar[1] = ar[1]/2.0
  ar[n+1] = ar[n+1]/2.0
  wsp = Array(Float64,n+1)
  for i = 1:n+1
    wsp[i] = wielomianC(ar,e[i])
  end
  wsp[1] = wsp[1]/2.0
  wsp[n+1] = wsp[n+1]/2.0
  return wsp
end

#funckja obliczająca wewnętrzne sumy we wzorze na K_n,tzn. wewnętrzne kombinacje liniowe wielomianów Czebyszewa
function wsp_Kn(f::Function)
  ar = Array(Float64,n+2)
  for i = 1:n+2
    ar[i] = f(e2[i])
  end
  ar[1] = ar[1]/2.0
  ar[n+2] = ar[n+2]/2.0
  wsp = Array(Float64,n+1)
  for i = 1:n+1
    wsp[i] = wielomianC(ar,e2[i])
  end
  wsp[1] = wsp[1]/2.0
  return wsp
end

#funckja tworząca bazę wielomianów Czebyszewa
function czebyszew()
  czeb = eye(n+2)
  czeb = zero(czeb)
  czeb[1] = 1.0
  czeb[n+4] = 1.0
  for i = 3:n+2
    pom = 2*razyx(czeb[:,i-1])
    czeb[:,i] = pom - czeb[:,i-2]
  end
  return czeb
end

#funkcja oblicza dyskretny iloczyn skalarny wielomianów w1 i funkcji f na zbiorze punktów ar
#wielomiany reprezentujemy jako tablicę gdzie w[i] to współczynnik przy x^(i-1)
function skalarf(ar::Array, f::Function, w1::Array)
  res = 0.0
  for i = 1:length(ar)
    res += wielomian(w1,ar[i])*f(ar[i])
  end
  return res
end

#funkcja obbliczająca wartość wielomianu w punkcie za pomocą schematu hornera
function wielomian(ar::Array, x::Float64)
  res = 0.0
  k = length(ar)
  for i = 1:k
    res = res*x + ar[k-i+1]
  end
  return res
end

#funkcja obliczająca wartość wielomianu rozpisanego w bazie wielomianów Czebyszewa
#za pomocą uogólnionego algorytmu Clenshawa
function wielomianC(ar::Array, x::Float64)
v0,v1,v2 = 0.0,0.0,0.0
  k = length(ar)
  for i = 1:k-1
    a = ar[k+1-i]
    v0 = a + 2*x*v1 - v2
    v2 = v1
    v1 = v0
  end
  v0 = ar[1] + x*v1 - v2
  return v0
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

#funkcja obliczająca wartość I_n w punkcie x dla funkcji f
function wartoscI_n(x::Float64,f::Function)
  wsp = wsp_In(f)
  y = 2.0*wielomianC(wsp,x)/convert(Float64,n+1)
  return y
end

#funkcja obliczająca wartość J_n w punkcie x dla funkcji f
function wartoscJ_n(x::Float64,f::Function)
  wsp = wsp_Jn(f)
  y = 2.0*wielomianC(wsp,x)/convert(Float64,n)
  return y
end

#funkcja obliczająca wartość K_n w punkcie x dla funkcji f
function wartoscK_n(x::Float64,f::Function)
  wsp = wsp_Kn(f)
  y = 2.0*wielomianC(wsp,x)/convert(Float64,n+1)
  return y
end

#funkcja obliczająca przybliżony błąd aproksymacji jednostajnej I_n dla funkcji f
function bladI_n(f::Function)
  max = -1
  for i = 0:1000
    x = -1.0 + i*1.0/1000.0
    res = abs(f(x) - wartoscI_n(x,f))
    if res > max
        max = res
    end
  end
  return max
end

#funkcja obliczająca przybliżony błąd aproksymacji jednostajnej J_n dla funkcji f
function bladJ_n(f::Function)
  max = -1
  for i = 0:1000
    x = -1.0 + i*1.0/1000.0
    res = abs(f(x) - wartoscJ_n(x,f))
    if res > max
        max = res
    end
  end
  return max
end

#funkcja obliczająca przybliżony błąd aproksymacji jednostajnej K_n dla funkcji f
function bladK_n(f::Function)
  max = -1
  for i = 0:1000
    x = -1.0 + i*1.0/1000.0
    res = abs(f(x) - wartoscK_n(x,f))
    if res > max
        max = res
    end
  end
  return max
end

#Część obliczeniowa programu
# inicjujemy najważniejsze zmienne w programie:
# n - liczba punktów, ekstrema - ekstrema n-1 wielomianu Czebyszewa, z - pierwiastki n+1-tego wielomianu Czebyszewa, e -ekstrema
# n-tego wielomianu Czebyszewa, e2 - ekstrema n+1-pierwszego wielomianu Czebyszewa
n = 5
czeb = czebyszew()
z = generuj_zera(n+1)
e = generuj_ekstrema(n+1)
e2 = generuj_ekstrema(n+2)
f1 = x -> x^2 + sin(x)
f2 = x -> x^4 * cos(x)
f3 = x -> (log(sin(x)+10))^3
f4 = x -> x^2/(sin(x)+1.1) * exp(x)
F = [f1,f2,f3,f4]

bI = Array(Float64,4)
bJ = Array(Float64,4)
bK = Array(Float64,4)

for i = 1:4
  bI[i] = bladI_n(F[i])
  bJ[i] = bladJ_n(F[i])
  bK[i] = bladK_n(F[i])
end

#Tablice wartości błędów aproksymacji dla wielomianów I_n,J_n i K_n; i-ty element to błąd dla funkcji F[i]
bI
bJ
bK

#poniżej można zobaczyć wykresy naszych wielomianów i aproksymowanych funkcji - już dla n = 10 nie da się zauważyć różnicy
# miedzy wykresami
using Gadfly
plot([F[4],x -> wartoscI_n(x,F[4])],-1,1)
plot([F[1],x -> wartoscJ_n(x,F[1])],-1,1)
plot([F[2],x -> wartoscJ_n(x,F[2])],-1,1)
plot([F[3],x -> wartoscK_n(x,F[3])],-1,1)
