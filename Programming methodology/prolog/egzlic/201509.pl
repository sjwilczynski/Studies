p(L) :-
	append(A,B,L),
	\+ append(B,A,L),!.

dif(L) :- 
	(L = [X,Y|A], X \= Y, !);
	(L = [X,Y|A], dif([Y|A])).
	
