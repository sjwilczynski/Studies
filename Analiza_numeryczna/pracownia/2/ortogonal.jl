#wielomiany Grama
#dlaczego tu wychodzą wielomiany czebyszewa - te punkty to ekstrema a nie zera tego wielomianu!!!!
# tw o wielomianie interpolujacym w kestremach czebyszewa(przy czebyszewie)
n = 6
punkty = Array(Float64,n)
p1 = Array(Float64,n)
for i = 1:n
  if i == 1 || i == n
    p1[i] = 0.5
  else
    p1[i] = 1.0
  end
  punkty[i] = cos((i-1)*pi/(n-1))
end
#println(arr)
#println(p1)

function skalar(ar::Array, f::Function, g::Function, p::Function)
  res = 0.0
  for i = 1:length(ar)
    res += f(ar[i])*g(ar[i])*p(ar[i])
  end
  return res
end

function sk(ar::Array, w1::Array, w2::Array, p::Array)
  res = 0.0
  for i = 1:length(ar)
    res += wielomian(w1,ar[i])*wielomian(w2,ar[i])*p[i]
  end
  return res
end

function wielomian(ar::Array, x::Float64)
  res = 0.0
  for i = 1:length(ar)
    res += ar[i]*x^(i-1)
  end
  return res
end

function makebaza(n)
  baza = Array(Float64,n*n)
  for i in 1:n*n
    baza[i] = 0.0
  end
  for i in 1:n
    baza[(i-1)*n + i] = 1.0
  end
  return baza
end
function razyx(ar)
  n = length(ar)
  for i = 0:n-2
    ar[n-i] = ar[n-i-1]
  end
  ar[1] = 0
  return ar
end



#println(razyx([1,0,100,40,2,7,6,0]))

function orto2(n,p1)
  baza = Array(Float64,n*n)
  bazap = Array(Float64,n)
  baza[1] = 1.0
  bazap[1] = 1.0
  for i = 2:n
      baza[i] = 0
      bazap[i] = 0
  end
  baza[n+1] = -sk(punkty,razyx(bazap[1:n]),baza[1:n],p1)/sk(punkty,baza[1:n],baza[1:n],p1)
  baza[n+2] = 1.0
  for i = 3:n
      baza[n+i] = 0
  end
  for i = 3:n
    k = (i-1)*n
    bazap = razyx(baza[k-n + 1 : k])
    i1 = sk(punkty,bazap[1:n],baza[k-n+1:k],p1)
    i2 = sk(punkty,baza[k-n+1:k],baza[k-n+1:k],p1)
    i3 = sk(punkty,baza[k-2*n+1:k-n],baza[k-2*n+1:k-n],p1)

    c = i1/i2
    d = i2/i3
    for j = 1:n
        baza[k+j] = bazap[j] - c*baza[k-n+j] - d*baza[k-2*n+j]
    end
  end
  return baza
end




function orto1(n,p1)
  baza = makebaza(n)
  nbaza = Array(Float64,n*n)
  for i = 1:n
    nbaza[i] = baza[i]
  end
  for i = 2:n
      for p = 1:n
        k = (i-1)*n
        suma = 0
        fi = baza[k+1:k+n]
        for j = 1:i-1
          gj = nbaza[(j-1)*n + 1:j*n]
          suma +=  (sk(punkty,fi,gj,p1)/sk(punkty,gj,gj,p1))*gj[p]
        end
        #println(suma)
        nbaza[k+p] = baza[k+p] - suma
      end
  end
  return nbaza
end





b = makebaza(n)
or = orto1(n,p1)
println(or)
println(sk(punkty,or[(n-2)*n+1: n*n - n],or[n*n-n+1: n*n],p1))
println(punkty)
orrr = orto2(n,p1)
println(orrr)
println(sk(punkty,orrr[n*n-2*n+1:n*n-n],orrr[n*n-n+1:n*n],p1))
