[lena, map] = imread('Lena.gif');
colormap(map);
lena = double(lena);
[ca,ch,cv,cd] = dwt2(lena,'db6');
imagesc(ca)
figure(2)
[ca,ch,cv,cd] = dwt2(ca,'db6');
colormap(map)
imagesc(ca)
figure(3)
[ca,ch,cv,cd] = dwt2(ca,'db6');
colormap(map)
imagesc(ca)