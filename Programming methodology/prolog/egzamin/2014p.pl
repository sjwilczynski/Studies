%zad1
max(X,Y,Y):-
	Y > X,!.
max(X,_,X).
%zad10
succ([],[1]).
succ([0|T],[1|T]):-!.
succ([1|T],[0|S]):-
	succ(T,S).
%zad11
p([_,_,_]).
p([_,_,_,_|T]):-
	p(T).

