[bird,map] = imread('Bird.gif');
colormap(map);
bird = double(bird);
[i,j] = size(bird);
mask = zeros(i,j);
n1 = 0.2;
n2 = 0.4;
r_max = (i/2-0.5)^2 + (j/2-0.5)^2;
for k=1:i
    for l=1:j
        r = (k-i/2-0.5)^2 + (l-j/2-0.5)^2; %odleglosc od srodka obrazka
        r = r/r_max;
        if r <= n1
            mask(k,l) = 1;
        elseif r >= n2 
                mask(k,l) = 0;
        else 
            mask(k,l) = -(r-n1)/(n2-n1) + 1;
        end
    end
end

C = 255*ones(i,j);
im = C -(C-bird).*mask;
image(im)
        