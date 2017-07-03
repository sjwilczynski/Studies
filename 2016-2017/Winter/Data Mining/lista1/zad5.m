function [I] = zad5(X,Y)
        Dp = bsxfun(@plus,sum(bsxfun(@times,X,X))',sum(bsxfun(@times,Y,Y)));
        D = sqrt(bsxfun(@minus, Dp, 2*X'*Y));
	[M,I] = min(D);                                  
