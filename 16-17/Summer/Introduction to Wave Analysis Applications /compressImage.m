function k = mojaFalka(plik,falka)
%zwraca stopien kompresji
[obrazek, map] = imread(plik);
colormap(map);
obrazek = double(obrazek);
image(obrazek)
[C,S] = wavedec2(obrazek,9,falka);
j = size(C,2);
threshold = 50;
mask = abs(C) <= threshold;
mask1 = C > threshold;
mask2 = C < -threshold;
C(mask) = 0;
C(mask1) = C(mask1) - threshold;
C(mask2) = C(mask2) + threshold;
k = sum(mask);
X = waverec2(C,S,falka);
figure(2);
colormap(map);
image(X);
k = k/j;
end