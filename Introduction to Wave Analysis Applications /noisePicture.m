function B = zaszumObraz(obraz)
% zaszumiamy obraz convem
[A,map] = imread(obraz);
A = double(A);
colormap(map);
mask=[2 4 5 4 2;4 9 12 9 4;5 12 15 12 5;4 9 12 9 4;2 4 5 4 2];
mask = mask/159;
B = conv2(A,mask, 'same');
B = conv2(B,mask, 'same');
imwrite(B, 'noDetail.gif');
