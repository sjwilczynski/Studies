[A,map] = imread('brain.gif');
figure(1);
colormap(map);
A = double(A);
[ca,ch,cv,cd] = dwt2(A,'db4');

imagesc(my_edge(ca));
figure(2);
[ca,ch,cv,cd] = dwt2(ca,'db4');
colormap(map);
imagesc(my_edge(ca));
figure(3);
colormap(map);

imagesc(my_edge(ch+cv+cd));
%{
figure(4);
colormap(map);
imagesc(my_edge(cv));
figure(5);
colormap(map);
imagesc(my_edge(cd));
%}

