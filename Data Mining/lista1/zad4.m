function [D] = zad4(X,Y)
%transpozycja daje nam to co by dal repmat - wszystkie pary sum
	Dp = bsxfun(@plus,sum(bsxfun(@times,X,X))',sum(bsxfun(@times,Y,Y)));
	D = sqrt(bsxfun(@minus, Dp, 2*X'*Y));
