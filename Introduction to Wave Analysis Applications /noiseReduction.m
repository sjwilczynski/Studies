[lena, map] = imread('LenaNoise.gif');
lenaorg = imread('Lena.gif');
colormap(map);
lena = double(lena);
zero = zeros(size(lena));
mask=[2 4 5 4 2;4 9 12 9 4;5 12 15 12 5;4 9 12 9 4;2 4 5 4 2];
mask = mask/159;
bezszumu = conv2(lena,mask, 'same');
bezszumu = conv2(bezszumu,mask, 'same');
bezszumu = conv2(bezszumu,mask, 'same');
lena = uint8(lena);
bezszumu = uint8(bezszumu);
image(lena);
figure(2)
colormap(map);
image(bezszumu);
figure(3)
colormap(map);
image(lenaorg);