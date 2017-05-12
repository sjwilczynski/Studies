function [K] = zad5b(X,Y,k)
        Dp = bsxfun(@plus,sum(bsxfun(@times,X,X))',sum(bsxfun(@times,Y,Y)));
        D = sqrt(bsxfun(@minus, Dp, 2*X'*Y));
	[M,I] = sort(D);
	K = I(1:k,:);                                   
