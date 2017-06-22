[lena, map] = imread('Lena.gif');
colormap(map);
B = double(lena);
B = fftshift(fft2(B));
mask = zeros(512);
mask(200:311,200:311) = 1; %255ones(112)
C = B.*mask;
C = ifftshift(C);
A = ifft2(C);
image(abs(A));
figure(2)
colormap(map);
image(lena)

