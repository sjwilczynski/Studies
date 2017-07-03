d = 100;
x = rand(d,1)+2;
y = rand(d,1)+3;
w = rand(d,1);

l = sqrt(x'*x);
av = (w'*x)/sum(w);
dist = sqrt( (x-y)'*(x-y));
sk = y'*x;

N = 1000;

M = rand(d,N);
dlugosci = sqrt(sum(bsxfun(@times,M,M)));
srednie = sum(bsxfun(@times,w,M))/sum(w);
Mi = bsxfun(@minus,M,y);
odleglosci = sqrt(sum(bsxfun(@times,Mi,Mi)));
iloczyny = sum(bsxfun(@times,y,M));


