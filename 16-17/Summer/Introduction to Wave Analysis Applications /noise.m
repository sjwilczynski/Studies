[lena, map] = imread('Lena.gif');
colormap(map);
lena = double(lena);
[row,col] = size(lena);
normal = 20*randn(row, col);
szum = lena + normal;
szum = uint8(szum);
image(szum);
imwrite(szum, 'LenaNoise.gif');




