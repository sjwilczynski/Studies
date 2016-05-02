function generatePoints(r)
  for i in 1:r
    points[i] = cos((i-1)*pi / (r-1))
  end
end
function p(x) #funkcja wagowa z treści
  if x == 1 || x == dim
    return 0.5
  else
    return 1.0
  end
end
function calc(w, b) #oblicza wielomian w punkcie
  wyn = 0.0
  for i in 1:dim
    wyn = wyn*b + wekt[w, dim - i + 1]
   # println(wyn)
  end
  return wyn
end
function lengthSq(v1)
  wyn = 0.0
  nr = 1
  for i in points
    w = calc(v1, i)
    wyn  += w * w * p(nr)
    nr+=1
  end
  return wyn
end
function iloczynSkalarny(v1, v2)
  wyn = 0.0
  nr = 1
  for i in points
      wyn  += calc(v1, i)*calc(v2, i) * p(nr)
      nr += 1
  end
  wyn
end
function krok(w, v) #funckja ortogonalizujaca w tablicy
  wsp = iloczynSkalarny(w,v) / lengthSq(v)
  #println(wsp)
  for i in 1:dim
    wekt[w,i] = wekt[w,i] - wekt[v,i] * wsp
  end
end
function gramSchmidt()
  for v in 1:dim#bierzemy kolejny wektor do ortogonalizacji
    for v2 in 1:(v-1) #bierzemy już zortogonalizowane wektory
      krok(v, v2)
      #println(wekt)
    end
  end
end
function checkOrt()
  max_err = 0
  sum_err = 0
  for i in 1:dim
    for j in 1:(i-1)
   #   @printf("%.16f ", iloczynSkalarny(i,j))
        il = abs(iloczynSkalarny(i,j))
        if max_err < il
          max_err = il
        end
        sum_err += il
    end
  #  @printf("\n")
  end
  @printf("Norma Max %.32f\n",max_err)
  @printf("Norma Sum %.32f\n",sum_err)
end
function calcC(w)
  wyn = 0.0
  nr = 1
  for i in points
      wyn  += i * calc(w, i)*calc(w,i ) * p(nr)
      nr += 1
  end
  return wyn
end
function rekur()
  #P_0
  wekt[1, 1] = 1
  for i in 2:dim
    wekt[1, i] = 0
  end
  #P_1
  for i in 3:dim
    wekt[2, i] = 0
  end
  wekt[2, 2] = 1
  il2 = lengthSq(1)
  wekt[2, 1] = calcC(1) / il2
  #P_n
  for i in 3:dim
    il1 = lengthSq(i-1)
    c = calcC(i-1) / il1
    d =  il1 / il2
    il2 = il1
    wekt[i,1] = - (c*wekt[i-1, 1] + d*wekt[i-2, 1])
    for j in 2:dim
      wekt[i,j] = wekt[i-1, j-1] - (c*wekt[i-1, j] + d*wekt[i-2, j])
    end
  end
end
#wekt = [1 2 3; 4 5 6; 7 8 9; 10 11 12]
dim = 10
wekt = eye(dim)
#wielomiany są postaci w[x,1]*x^2 + w[x,2]*x + w[x,3]
points = Array(Float64,dim)
generatePoints(dim)
println(points)
@printf("Dim:%d\n", dim)
println("Gram-Schmidt:")
gramSchmidt()
println(wekt)
checkOrt()
#println("Gram-Schmidt 2 raz:")
#gramSchmidt()
#checkOrt()
#println("Rekurencyjnie")
#wekt = eye(dim)
#rekur()
#checkOrt()
wekt
