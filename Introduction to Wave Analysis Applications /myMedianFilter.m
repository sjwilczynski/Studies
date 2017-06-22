function C = myMedianFilter( obraz )
%MYMEDIANFILTER Summary of this function goes here
%   Detailed explanation goes here
C = imread(obraz);
[m,n] = size(C);
lista = zeros(9,1);
%Modified filter 
for i=2:m-1
    for j=2:n-1
            lista = reshape(C(i-1:i+1,j-1:j+1),9,1);
            lista = sort(lista);
            C(i,j) = lista(5);
    end
end 
end


