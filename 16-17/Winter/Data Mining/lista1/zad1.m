x = 2*rand(100000,1)-1;
y = 3*randn(100000,1)+5;
x1 = sqrt(5)*randn(100000,1)+2;
x2 = randn(100000,1)+3;

figure;
hold on;
hist(x,100);
title('Wykres 1');
print('-dpng', '-r300', 'wykres1.png');
close;

figure;
hold on;
hist(y,100);
title('Wykres 2');
print('-dpng', '-r300', 'wykres2.png');
close;

figure;
hold on;
plot(x1,x2,'.');
print('-dpng', '-r300', 'wykres3.png');
close;

z = x2 - x1;
[row col] = find(z>0);
p1 = size(z(row, :))/size(z);

p2 = normcdf(0,1,6);
print(p1);
print(p2);
