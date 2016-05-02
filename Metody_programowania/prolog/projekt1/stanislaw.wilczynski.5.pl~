%Wyjasnienia dotyczace nazw zmiennych:

%wystpujaca sama litera N zawsze oznacza rozmiar zagadki,slowo generator oznacza jedna z liczb tworzaca iloczyny na planszy
% L_ oznacza liste, przedrostek C oznacza chilowosc, przedrostek N nowosc,przedrostek F splaszczenie, slowko Gen oznacza generator, slowko Wsp oznacza   wspolrzedne, Sas oznacza sasiada(jego wspolrzedne), przedrostek A przy liscie oznacza ze jest to akumulator,L_Wczytana to wczytana(i pozniej splaszczona) tablica reprezentujaca nasza plansze z tresci zadania

%Ogólnie o algorytmie: jesli mamy juz pewna liste znalezionych generatorow, bierzemy liczbe niemniejsza niz najwiekszy z nich i probujemy ja dodac do listy generatorow. Mowimy ze ta operacja powiedzie sie jesli uda nam sie znalezc na planszy iloczyny tego nowego generatora ze wszystkimi poprzednimi, oczywiscie wykluczjac pola ktore sa juz cyframi iloczynu innych generatorow(po to lista odwiedzonych wspolrzednych na planszy), jesli sie udalo powtarzamy operacje na dla nowej listy generatorow i nowej listy odzwiedzonych wspolrzednych. Jesli sie nie udalo maszyna prologowa wykonuje nawrot i probuje dodac liczbe o 1 wieksza do listy generatorow.

%Predykaty glowne: 

solve(N,L_Wczytana,(L_Gen,L_Wsp)):-
	N>1,
	Y is N-1,
	length(L_Wczytana,Y),	
	flatten(L_Wczytana,FL_Wczytana),
	X is N*(N-1), 
	length(FL_Wczytana,X),
	rozwiaz(N,[],CL_Wsp,[],CL_Gen,[],FL_Wczytana,(1,100)),
	reverse(CL_Gen,L_Gen),
	zamien(CL_Wsp,L_Wsp).

%predykat solve w pierwszych 6 linijkach sprawdza czy osoba wywolujaca program podala prawidlowe dane
%nastepnie wywoluje predykat rozwiaz(patrz opis tego predykatu ponizej) i zamienia jego wyniki za pomoca predykatow reverse i zamien na taka forme jaka jest wymagana w zadaniu

%odniesienie do wywolania predykatu rozwiaz: 1 argument to rozmiar zagadki, 2 to akumulator na liste wpolrzednych wystepowania iloczynow generatorow na planszy, 3 otrzymana lista wspolrzednych(przy wywolaniu nieukonkretniona),4 i 5 to analogiczne dla listy generatorow, 6 to lista odwiedzonych punktow na planszy(wystepujacych w jakims iloczynie), 7 splaszczona lista reprezentujaca plansze, 8 - poczatkowe minimum i maximum

rozwiaz(N,L_Wsp,L_Wsp,L_Gen,L_Gen,_,_,_):-
	length(L_Gen,N),!.
rozwiaz(N,AL_Wsp,L_Wsp,AL_Gen,L_Gen,L_Odzwiedzone,L_Wczytana,(Min,Max)):-
	liczba_z_przedzialu(X,Min,Max), 
	dodaj_generator(L_Wczytana,N,X,AL_Gen,AL_Wsp,(1,1),NAL_Gen,NAL_Wsp,L_Odzwiedzone,NL_Odzwiedzone),
	NAL_Gen = [NMin|_], %nalezy pamietac ze pierwszy element jest NAJWEKSZYM sposrod znalezionych generatorow
	NMax is 100//NMin,   % Nmax <= 100//Nmin bo w przeciwnym wypadku iloczyn nowej liczby i najwiekszego generatora przekroczylby 100
	rozwiaz(N,NAL_Wsp,L_Wsp,NAL_Gen,L_Gen,NL_Odzwiedzone,L_Wczytana,(NMin,NMax)).

%jesli akumulator listy generatorow ma juz N elementow(znalezlismy generatory) konczymy sukcesem i ukonkretniamy wczensiej nieukonkretnione L_Wsp i L_Gen(klauzula 1)
% w przeciwnym wypadku: genreujemy liczbe X z przedzialu [Min,Max] i probujemy ja dodac do listy generatorow, jesli sie udalo wyznaczamy nowe Max i nowe Min i nowe Max i wywolujemy predykat rozwiaz z powiekszonymi akumulatorami NAL_Wsp i NAL_Gen oraz nowa lista odwiedzonych elementow planszy (NL_Odwiedzone), i z para (NMin,NMax)

dodaj_generator(_,_,X,[],_,_,[X],_,_,[]):-!.
dodaj_generator(L_Wczytana,N,X,CL_Gen,CL_Wsp,Wsp,NCL_Gen,NCL_Wsp,L_Odwiedzone,NL_Odwiedzone):-
	NCL_Gen = [X|CL_Gen], %w ten sposob otrzymujemy liste generatorow w porzadku malejacym!!!
	znajdz_iloczyny(L_Wczytana,N,X,CL_Gen,CL_Wsp,NCL_Wsp,Wsp,L_Odwiedzone,NL_Odwiedzone).

%predykat sluzy do sprawdzenia czy mozliwe jest dodanie generatora X do chwilowej listy generatorow CL_Gen, jesli chwilowa lista generatorow jest pusta, dodajemy generator(pierwsza klauzula), jesli nie jest, probujemy dodac generator do listy. Uda sie to jesli znajdziemy na planszy iloczyny nowego generatora ze wszystkimi innymi generatorami bedacymi juz w CL_Gen(tym zajmuje sie predykat znajdz_iloczyny opisany ponizej)
%argumenty predykatu dodaj_generator sa potrzebne w predykacie znajdz_iloczyny wytlumaczonym ponizej
 
sprawdz(L_Wczytana,N,X,Gen,L_Odzwiedzone,Wsp,Sas):-
	tablica(Wsp,K1,L_Wczytana,N),
	sasiad(Wsp,Sas,N),
	\+member(Sas,L_Odzwiedzone),
	tablica(Sas,K2,L_Wczytana,N),
	K1 > 0,
	Z is 10*K1 + K2,
	Y is X*Gen,
	Z == Y.
%predykat sluzy do sprawdzenia czy liczba z planszy(Z) stworzona z cyfr pol o wpolrzednych Wsp i Sas odpowiednio
%jest iloczynem generatora(X),który probujemy dodac i jednego z generatorow(Gen) juz znajdujacych sie na liscie 

znajdz_iloczyny(_,_,_,[],L_Wsp,L_Wsp,_,L_Odwiedzone,L_Odwiedzone):-!.
znajdz_iloczyny(L_Wczytana,N,X,L_Gen,L_Wsp,NL_Wsp,Wsp,L_Odzwiedzone,NL_Odzwiedzone):-
	member(Wsp,L_Odzwiedzone),!,
	nastepny(Wsp,NWsp,N),
	znajdz_iloczyny(L_Wczytana,N,X,L_Gen,L_Wsp,NL_Wsp,NWsp,L_Odzwiedzone,NL_Odzwiedzone).
znajdz_iloczyny(L_Wczytana,N,X,[H|L_Gen],L_Wsp,NL_Wsp,Wsp,L_Odzwiedzone,NL_Odzwiedzone):-
	sprawdz(L_Wczytana,N,X,H,L_Odzwiedzone,Wsp,Sas),
	X >= H,
	wstaw(((Wsp,Sas),(H,X)),L_Wsp,CL_Wsp),
	CL_Odwiedzone = [Wsp,Sas|L_Odzwiedzone],
	znajdz_iloczyny(L_Wczytana,N,X,L_Gen,CL_Wsp,NL_Wsp,(1,1),CL_Odwiedzone,NL_Odzwiedzone).
znajdz_iloczyny(L_Wczytana,N,X,[H|L_Gen],L_Wsp,NL_Wsp,Wsp,L_Odzwiedzone,NL_Odzwiedzone):-
	sprawdz(L_Wczytana,N,X,H,L_Odzwiedzone,Wsp,Sas),
	X < H,
	wstaw(((Wsp,Sas),(X,H)),L_Wsp,CL_Wsp),
	CL_Odwiedzone = [Wsp,Sas|L_Odzwiedzone],
	znajdz_iloczyny(L_Wczytana,N,X,L_Gen,CL_Wsp,NL_Wsp,(1,1),CL_Odwiedzone,NL_Odzwiedzone).
znajdz_iloczyny(L_Wczytana,N,X,L_Gen,L_Wsp,NL_Wsp,Wsp,L_Odzwiedzone,NL_Odzwiedzone):-
	nastepny(Wsp,NWsp,N),
	znajdz_iloczyny(L_Wczytana,N,X,L_Gen,L_Wsp,NL_Wsp,NWsp,L_Odzwiedzone,NL_Odzwiedzone).

%najwazniejszy predykat w zadaniu 
%argumenty: 1 - nasza plansza, 2 - rozmiar zagadki, 3 - generator ktory probujemy dodac, 4 - lista wczesniej znalezionych generatorow, 5 - lista wspolrzednych wystepowania iloczynow generatorow, 6 - nowa lista tych iloczynow(takze z generatorem X),7 - Wsp od ktorych zaczynamy przeszukiwanie planszy, 8 - obecna lista wspolrzednych odwiedzonych, 9 - lista odwiedzonych otrzymana po zakonczeniu pracy predyaktu 

% klauzula 1:lista generatorow zostala oprozniona => iloczyny nowego generatora ze wszystkimi innymi z listy zostaly znalezione, wiec mozemy dodac nasz generator do listy generatorow i otrzymujemy nowa L_Wsp i L_Odwiedzone
%klauzula 2: jesli pole o wspolrzednych Wsp(czyli to w ktorym sie znajdujemy) jest juz odwiedzone to nie moze byc cyfra iloczynu nowego generatora ze starym. W takim razie idziemy do nastepnego pola(o wspolrzednych NWsp) i wywolujemy w nim predykat znajdz_iloczyny
%klauzula 3 i 4: wykonujemy sprawdzenie czy liczba stworzona z cyfr pol o wspolrzedncyh Wsp i Sas to iloczyn nowego generatora ze starym, jesli tak musimy zaktualizowac nasza liste wspolrzedncyh(L_Wsp) i liste odwiedzonych(L_Odwiedzone) o punkty Wsp i Sas, nastepnie wywolujemy predykat znajdz_iloczyny z uaktualnionymi danymi z jednym generatorem na L_Gen mniej i zaczynamy szukac od poczatku planszy (1,1); sa tu rozdzielone 2 przypadki potrzebne zeby otrzymac nasza L_Wsp w kolejnosci wymaganej w zadaniu(nie tylko podajemy pare wspolrzedncyh wystepowania iloczynow generatorow ale rowniez jakie to generatory w kolejnosci najpierw mniejszy potem wiekszy
%klauzula 5: jesli zadna poprzednia nie zostala spelniona nalezy przejsc do nastepnego pola i w nim wywolac predykat.

%Predykaty pomocnicze:

liczba_z_przedzialu(X,A,B):-          %predykat znajdujacy liczbe X z przedzialu 
	liczba(X,A,B,A).		%[A,B], wywoluje on 4-argumentowy 	
liczba(A,A,B,_):-			%predykat liczba z 4. elementem akumulatorem
	A =< B.			
liczba(X,A,B,C):-			%tryb(-,+,+)
	C<B,
	liczba(X1,A,B,C+1),
	X is X1+1.

flatten(List, Flattened):-
  	flatten(List, Flattened, []).	%predykat omawiany na cwiczeniach sluzacy
flatten([], Flattened, Flattened).	%do splaszczania listy, splaszczamy nim wejsciowa liste z zadania
flatten([H|T], [H|Flattened], Acc):-	%dla wygody piszacego program 
  	atomic(H), !,			%tryb(+,-)
  	flatten(T, Flattened, Acc).
flatten([H|T], Flattened, Acc):-
  	flatten(T, TailFlattened, Acc),
  	flatten(H, Flattened, TailFlattened).

tablica((A,B),H,L_Wczytana,N):-		% predykat znajdujacy wartosc H
	Z is (A-1)*N+B-1,		% w polu na planszy o wspolrzednych (A,B)
	length(L1,Z),			
	append(L1,L2,L_Wczytana),		%tryb(+,-,+,+)
	L2 = [H|_].

sasiad((X,Y),(A,B),N):-                 %predykat sasiad znajduje wszystkich sasiadow
	A is X+1,			% punktu o wspolrzednych (X,Y) i zwraca  
	B = Y,				%wspolrzedne tego sasiada(A,B) na planszy
	A =< N-1,A >= 1,B =< N,B >= 1.	
sasiad((X,Y),(A,B),N):-			%tryb(+,-,+)
	A is X+1,			%N jest potrzebne zeby sprawdzic czy nie
	B is Y+1,			%wychodzimy poza granice planszy
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A is X+1,
	B is Y-1,
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A = X,
	B is Y+1,
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A is X-1,
	B is Y+1,
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A is X-1,
	B = Y,
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A is X-1,
	B is Y-1,
	A =< N-1,A >= 1,B =< N,B >= 1.
sasiad((X,Y),(A,B),N):-
	A = X,
	B is Y-1,
	A =< N-1,A >= 1,B =< N,B >= 1.

nastepny((A,B),(X,Y),N):-	%predykat ktory dla zadanych wspolrzednych (A,B)
	B < N,!,		%znajduje nastepny element planszy(liczymy z lewej
	B1 is B+1,		% na prawo i z gory na dol)
	X = A,B1 = Y.
nastepny((A,_),(X,Y),N):-	%tryb(+,-)
	A1 is A+1,
	A1 =< N-1,
	X = A1,Y = 1.

wstaw(X,[],[X]):-!.		%predykat wstawiajacy znalezione wspolrzedne iloczynu
wstaw(X,[H|L],[X,H|L]):-	%nowego generatora ze starym do listy wspolrzednych
	X = ((_,_),(A,_)),	% w ten sposob,aby zachowana byla kolejnosc
	H = ((_,_),(C,_)),	%wymagana w tresci zadania (bardzo podobny do 
	A < C,!.		% omawianego na cwiczeniach predykatu insert
wstaw(X,[H|L],[X,H|L]):-	%wstawiajacego element do listy z zachowaniem
	X = ((_,_),(A,B)),	%kolejnosci rosnacej)
	H = ((_,_),(C,D)),	
	A == C,			%tryb(+,+,-)
	B =< D,!.
wstaw(X,[H|L],[H|R]):-
	wstaw(X,L,R).

zamien([],[]).			%predykat zamieniajacy liste wspolrzednych
zamien([H|L],[NH|NL]):-		%wystepowania poszczegolnych iloczynow generatorow
	H = (X,_,_),		%razem z ich wartosciami na liste wymagana w tresci
	X = (Y,A,B),		%zadania
	NH = (Y,(A,B)),		
	zamien(L,NL).		%tryb(+,-)

%testy:

%Raport z dzialania check_solution:
%wszystkie testy zostaly zatwierdzone. Czas wykonywania to 40 sekund
%z czego 30 sekund trwa wykonywanie ostatniego student_count_test rozmiaru 6

student_simple_test(2,[[9,6]],([3, 23], [ ((1, 2), (1, 1))])).
student_simple_test(3,[[8,6,8],[1,4,9]],([3, 6, 16], [ ((2, 1), 1, 1), ((2, 2), 1, 3), ((2, 3), 1, 2)])).
student_simple_test(3,[[1,3,4],[3,2,4]],([3, 4, 11], [ ((1, 1), 2, 2), ((1, 2), 2, 1), ((1, 3), 2, 3)])).
student_simple_test(3,[[6,4,0],[0,9,6]],([5, 8, 12], [ ((1, 2), 2, 1), ((2, 3), 1, 3), ((2, 2), 1, 1)])).
student_simple_test(4,[[9,5,1,5],[6,3,3,2],[9,1,5,1]],([3, 5, 7, 13], [ ((1, 3), 1, 4), ((2, 4), 3, 4), ((2, 2), 1, 1), ((2, 3), 3, 3), ((2, 1), 1, 2), ((3, 1), 3, 2)])).
student_simple_test(4,[[4,9,2,8],[4,7,9,6],[7,6,3,3]],([4, 7, 9, 11], [ ((1, 3), 1, 4), ((3, 3), 3, 2), ((1, 1), 2, 1), ((2, 4), 3, 4), ((2, 2), 3, 1), ((1, 2), 2, 3)])).
student_simple_test(4,[[9,5,1,5],[6,3,3,2],[9,1,5,1]],([3, 5, 7, 13], [ ((3, 4), 3, 3), ((2, 4), 1, 3), ((2, 2), 1, 1), ((2, 3), 1, 4), ((2, 1), 1, 2), ((3, 1), 3, 2)])).  
student_simple_test(5,[[9,8,2,6,8],[1,6,1,4,4],[4,2,1,2,9],[3,6,8,6,4]],([3,4,6,6,16], [ ((2,3),1,3), ((3,3),4,3), ((2,1),1,2), ((2,5),1,5), ((3,4),4,5), ((3,2),3,1), ((1,4),2,4), ((4,1),4,2), ((3,5),4,4), ((1,1),2,2)])).
student_simple_test(5,[[4,6,9,8,4],[1,8,2,2,3],[8,7,1,6,4],[4,2,6,5,2]], ([3,6,7,8,12], [ ((2,1),3,1), ((2,4),3,3), ((4,2),4,1), ((2,5),3,4), ((3,5),4,5), ((1,1),2,2), ((3,2),2,3), ((4,4),4,3), ((1,4),1,5), ((1,3),1,2)])).
student_simple_test(6,[[2,6,4,2,3,2],[5,3,0,4,0,1],[1,0,2,7,0,7],[2,9,8,6,7,6],[0,2,8,3,3,2]], ([3,4,7,8,9,10], [ ((2,6),1,6), ((4,1),3,1), ((1,4),2,4), ((5,6),4,5), ((1,5),2,5), ((5,2),5,3), ((2,2),1,1), ((5,5),4,6), ((1,3),2,3), ((2,1),1,2), ((4,4),5,4), ((3,6),3,5), ((3,4),3,3), ((4,3),3,2), ((4,2),5,1)])).

student_count_test(3,[[8,2,6],[0,4,0]],2).
student_count_test(3,[[4,2,5],[0,4,5]],4).
student_count_test(4,[[5,2,8,6],[6,2,3,5],[9,4,8,2]],12).
student_count_test(4,[[2,2,9,8],[4,8,4,8],[1,2,1,4]],11).
student_count_test(5,[[4,4,4,4,4],[4,4,4,4,4],[5,5,5,5,5],[6,6,6,6,6]],0).
student_count_test(5,[[6,0,2,4,0],[0,6,4,3,2],[8,1,8,2,1],[6,3,1,4,0]],32).
student_count_test(6,[[2,6,4,2,3,2],[5,3,0,4,0,1],[1,0,2,7,0,7],[2,9,8,6,7,6],[0,2,4,3,3,2]],0).
