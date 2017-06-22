[lena, map] = imread('Lena.gif');
colormap(map);
B = double(lena);
B = fft2(B); %dyskretna fft
C = abs(B); %oceniamy wspolczynniki
image(fftshift(C/20)); % zeby bylo cos widac
figure(2);
D = angle(B) + pi;  %bo zwraca od -pi do pi
image(fftshift(D*128/pi)) %zeby byly wartosci od 0 do 256
%%oceniamy argument

