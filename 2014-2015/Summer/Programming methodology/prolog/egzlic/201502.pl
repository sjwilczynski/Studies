p([]).
p(Xs) :-
	select(X,Xs,Rest),
	select(X,Rest,Rest2),
	p(Rest2).

parzyscie([]).
parzyscie(Xs) :-
	Xs = [_,_|Ys],
	parzyscie(Ys).
