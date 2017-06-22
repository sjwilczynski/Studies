mask=[2 4 5 4 2;4 9 12 9 4;5 12 15 12 5;4 9 12 9 4;2 4 5 4 2];
mask = mask/159;  %maska gaussowska
[A,map] = imread('Bird.gif');
colormap(map);
A = double(A);
A = conv2(A, mask, 'same'); %nalożenie maski gaussa na obrazek

Dx = [-1 0 1; -2 0 2 ; -1 0 1];
Dy = Dx';  % maski Prewiita do obliczania gradientu
Gx = conv2(A,Dx,'same');
Gy = conv2(A,Dy,'same');

%{
image(Gx);
figure(2);
colormap(map)
image(Gy);   %ogladanie gradientu
%}


G = abs(Gx) + abs(Gy);
T = atan(Gy./Gx);
T(isnan(T)) = 0;
%imagesc(T);
%kwantyzujemy kierunki: (pionowy, poziomy, w prawo gora, w prawo w dol)
D = floor(mod(4*T/pi + 2.5,4));
Xoffset = [-1,1,-1,1,0,0,-1,1];
Yoffset = [0,0,-1,1,-1,1,1,-1]; %offset sasiadow wzdluz kierunku gradientu
[m,n] = size(G);
Gthin = zeros(m,n);
for i=2:m-1
    for j = 2:n-1
        t = 2*D(i,j)+1;
        a = G(i + Xoffset(t), j + Yoffset(t));
        b = G(i + Xoffset(t+1), j + Yoffset(t+1));
        if a > G(i,j) || b > G(i,j)
            Gthin(i,j) = 0;
        else
            Gthin(i,j) = G(i,j);
        end
    end
end

image(G);
figure(2);
colormap(map);
image(Gthin);


%progowanie

thresh_high = max(max(G))*0.06;
thresh_low = thresh_high/2;
edge = Gthin > thresh_high;
t = 1;
while t
    t = 0;
    for i = 2:m-1
        for j = 2:n-1
            if edge(i,j)
                s = 2*D(i,j) + 1;
                for k = 0:1
                    a = Gthin(i - Yoffset(s+k), j + Xoffset(s+k));
                    if a > thresh_low && edge(i - Yoffset(s+k), j + Xoffset(s+k)) == 0
                        edge(i - Yoffset(s+k), j + Xoffset(s+k)) = 1;
                        t = 1;
                    end
                end
            end
        end
    end
end

figure(3);
colormap(map);
imagesc(edge);

Ys = Yoffset;
Xs = Xoffset;


%lepszy algorytm jakby stos zamiast przegladani wszytskeigo - przegladamy
%jzu tylko to co przed chwila dodalismy
stack = zeros(2,m*n);
stack_counter = 0;
 
for i = 2:m-1
    for j = 2:n-1
        if edge(i,j)
            stack_counter = stack_counter + 1;
            stack(:,stack_counter) = [i; j];
        end
    end
end
 
while stack_counter > 0
    %---pop---
    i = stack(1,stack_counter);
    j = stack(2,stack_counter);
    stack_counter = stack_counter - 1;
    %--------
   
    t = 2*D(i,j)+1;
    for k=0:1
        i_new = i-Ys(t+k);
        j_new = j+Xs(t+k);
        a = Gthin(i_new,j_new);
        if a > thresh_low && ~edge(i_new,j_new)
            edge(i_new,j_new) = 1;
            if i_new > 1 && i_new < m && j_new > 1 && j_new < n
                %---push---
                stack_counter = stack_counter + 1;
                stack(:,stack_counter) = [i_new; j_new];
                %----------
            end
        end
    end
end
 
figure(4);
colormap(map);
image(edge*256);

            

