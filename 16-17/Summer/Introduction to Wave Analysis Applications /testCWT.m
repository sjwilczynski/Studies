signal = dlmread('msignal.asc');
t = (0:1/255:1);
plot(t,signal);
scales = 2.^(0:0.05:7);
coef = cwt(signal,scales,'gaus1', '3Dlvl');
colormap(gray(256));
imagesc(coef);



t = (-0.5:0.001:0.5);
signal = sin(1./t);
figure(2);
plot(t,signal);
scales = 2.^(-0.25:0.0005:0.25);
coef = cwt(signal,scales,'gaus1');
figure(3);
colormap(gray(256));
imagesc(coef);