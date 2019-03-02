A = 170*ones(256);
B = 85*ones(86);
A(86:85+86,86:85+86) = B;
colormap(gray(256));
A = A + 20*randn(256);
mask=[2 4 5 4 2;4 9 12 9 4;5 12 15 12 5;4 9 12 9 4;2 4 5 4 2];
mask = mask/159;
image(A)
B = conv2(A,mask, 'same');
B = conv2(B,mask, 'same');
figure(2)
colormap(gray(256));
image(B)