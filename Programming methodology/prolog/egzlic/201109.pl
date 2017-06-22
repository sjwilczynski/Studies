len([],A,A).
len([_|T],A,N) :-
	A1 is A + 1,
	len(T,A1,N).

len(X,N) :-
	len(X,0,N).

insert([],X,[X]) :- !.
insert([H|T], E, [E,H|T]) :-
	E =< H,!.
insert([H|T], E, [H|R]) :-
	insert(T,E,R).

isort([],X,X).
isort([H|X],A,Y) :-
	insert(A,H,A1),
	isort(X,A1,Y).

isort(X,Y):-
	isort(X,[],Y).

setSize(X,N):-
	isort(X,Y),
	unique(Y,Z),
	len(Z,N).

unique([],[]).
unique([X],[X]).
unique([X,Y|T], R) :-
	X == Y, !,
	unique([Y|T],R).
	
unique([X,Y|T], [X|R]) :-
	unique([Y|T],R).
	
