import Puzzle
import Checker
import Data.List

--Uwagi ogólne:
--slowo generator oznacza jedną z liczb tworzącą iloczyny na planszy, listę oznaczamy przez dodanie na końcu nazwy zmiennej litery s, przedrostek c oznacza chwilowość danej zmiennej (np. cgens - oznacza tymczasową listę generatorów), pierwsza litera n w nazwie zmiennej oznacza nowość( zmienna po edytowaniu), t1 i t2 oznaczaką wyrażenia o wartości boolowskiej 

-- algorytm w tym programie jest prawie taki sam jak dla rozwiązania w prologu:
--jesli mamy już pewną listę znalezionych generatorów, bierzemy liczbę niewiększą niż najmniejeszy z nich(tutaj zmiana w porównaniu z rozwiązaniem z prologa - generatory próbujemy dokładać do listy od największego do najmniejszego) i próbujemy ją dodać do listy generatorow. Mówimy ze ta operacja powiedzie się, jeśli uda nam się znaleźć na planszy iloczyny tego nowego generatora ze wszystkimi poprzednimi, oczywiście wykluczjąc pola które są już cyframi iloczynu innych generatorów(po to lista odwiedzonych wspolrzędnych na planszy), jesli się udalo powtarzamy operacje na dla nowej listy generatorow i nowej listy odzwiedzonych wspolrzednych. Jesli się nie udalo dzięki nawrotom w wyrażeniach tablicowych w haskellu spróbujemy znaleźć inny generator.


type Plansza = [[Int]] -- typ planszy danej w zadaniu( planszę zawsze oznaczamy tab)
type Rozmiar = Int     -- rozmiar zagadki zawsze oznaczamy literką n
type Para = (Int,Int)  -- typ opisujący współrzędne pola na planszy (numerujemy od 1)

--funkcje główne:

solve :: Rozmiar -> Plansza -> [Result] 
solve n tab 		-- funkcja solve najpierw sprawdza wynik funkcji dobredane a następnie wywołuje funckję rozwiąż z pomocniczymi danymi ( analogicznie jak w programie prologowym)
	|dobredane n tab = [  fst x | x <- rozwiaz( n,tab,[],[],[],(1,99) )]
	|otherwise = []

dobredane:: Rozmiar -> Plansza -> Bool -- funkcja sprawdza czy test jest poprawnie napisany
dobredane n tab
	| ( (n>1) && (length tab == n-1) && ( (length$ concat tab) == n*(n-1) ) )= True
	| otherwise = False  


rozwiaz :: (Rozmiar,Plansza,[Int],[(Para,Para)],[Para],(Int,Int)) -> [(Result,[Para])]  --funkcja,działająca zgodnie z algorytmem opisanym powyżej
-- argumenty: cgens - chwilowa lista generatorów, chwilowa lista wpółrzędnych pól na których występują iloczyny generatorów z cgens, cvisits - chwilowa lista odwiedzonych współrzędnych, (min,max) - przedział, z którego możemy wybrać następny generator
rozwiaz(n,tab,cgens,cwsps,cvisits,(min,max)) 
		|length cgens == n = [((cgens,cwsps),cvisits)] -- jeśli znaleźliśmy n generatorów to (cgens,cwsps) jest naszym rozwiązaniem
		|otherwise = concat [ rozwiaz( n,tab,(x:cgens),(xs++cwsps),(ys++cvisits),(nmin,nmax) )|
					x <- [max,max-1..min], 
					xs <- wybierz$ map (iloczyn (n,tab,cvisits) x) cgens,
					let aux = unzip xs, let ys = (fst aux) ++ (snd aux),
					let nmin = 10 `div` x + 1, let nmax = mniejsze x (99 `div` x)]
--w przeciwnym wypadku łączymy(concat) listy wygenerowane przez wywołanie rekurencyjne:
-- x - wybieramy nowy generator, xs - znajdujemy współrzędne pól iloczynów nowego generatora z pozostałymi generatorami na planszy(opisy funkcji wybierz i iloczyn poniżej), ys - uaktualniamy liste odwiedzonych pól na planszy, nmin wyznaczamy tak, aby iloczyn dwóch najmniejszych generatorów był większy niż 10, nmax tak aby iloczyn dwóch największych był <= 99 i były znajdowane w porządku nierosnącym)  

iloczyn :: (Rozmiar,Plansza,[Para]) -> Int -> Int -> [(Para, Para)]
iloczyn (n,tab,visits) x y = [((a,b),(c,d))| a <- [1..n-1], b <- [1..n], c <- [1..n-1], d <- [1..n],
				      let v1 = wartosc (a,b) tab,
				      let v2 = wartosc (c,d) tab,
				      10*v1 + v2 == x*y,
				      let t1 = (a,b) `elem` visits, not t1,
				      let t2 = (c,d) `elem` visits, not t2,
				      (c,d) `elem` sasiad n (a,b)]
-- ta funkcja znajduje dla liczb x i y listę par sąsiednich wpółrzędnych na których występuje iloczyn tych dwóch liczb, nie biorąc pod uwagę pól już odwiedzonych (znajdujących się na liście visits)

wybierz :: [[(Para,Para)]] -> [[(Para,Para)]]
wybierz [] = [[]]
wybierz (x:xs) = [(a,b):ys| (a,b) <- x, ys <- wybierz xs, let zs = fst(unzip ys) ++ snd(unzip ys),
							let t1 = a `elem` zs, not t1,
						      let t2 = b `elem` zs, not t2]
-- najprościej działanie tej funkcji wytłumaczyć na przykładzie : "wybierz$ map (iloczyn (n,tab,cvisits) x) cgens", wyrażenie map (iloczyn (n,tab,cvisits) x) cgens generuje nam tablice, w której elemntami są wyniki funkcji iloczyn dla x i kolejnych generatorów z cgens, teraz chcemy z każdej takiej listy wybrać po jednym elemencie( po jednej parze współrzędnych ) tak, aby żadne współrzędne nie występowały w dwóch iloczynach(po to żeby każde pole planszy było przypisane do dokłądnie jednego iloczynu pewnych generatorów - w definicji naszej funkcji są opisują to warunki t1 i t2) - to właśnie robi funkcja wybierz


--funckje pomocnicze:
		
sasiad :: Rozmiar -> Para -> [Para] --funckja dla pola o danych współrzędnych (x,y) generuje listę sąsiadów tego pola
sasiad n (x,y) = [(x+a,y+b)| a <- [-1..1], b <- [-1..1], (x+a)>0, (y+b)>0, (a /= 0) || (b /= 0), (n-1) >= (x+a), n >= (y+b) ]

wartosc :: Para -> Plansza -> Int  -- funkcja zwraca liczbę znajdującą się na planszy w polu o współrzędnych (a,b)
wartosc (a,b) xs = ys !! (b-1)
	where ys = xs !! (a-1) 

mniejsze :: Int -> Int -> Int   --funckja zwracająca mniejszą z dwóch liczb
mniejsze x y 
	| x < y = x
	|otherwise = y


-- testy: 13 - łatwe(do rozmiaru 5), następne 7 wydajnościowe(6 wymiaru 6 i jeden wymiaru 7)
-- raport: łatwe zajmują w sumie ok 0,5sek a wydajnościowe w sumie ok 56sek
tests :: [Test]
tests = [
		SimpleTest
          (Puzzle 2 [[9, 6]])
          ([3, 23], [ ((1, 2), (1, 1))])
		,SimpleTest
          (Puzzle 3 [[8,6,8],[1,4,9]])
          ([3, 6, 16], [ ((2, 1), (1, 1)), ((2, 2), (1, 3)), ((2, 3), (1, 2))])
		,SimpleTest
          (Puzzle 3 [[1,3,4],[3,2,4]])
          ([3, 4, 11], [ ((1, 1), (2, 2)), ((1, 2), (2, 1)), ((1, 3), (2, 3))])
		,SimpleTest
          (Puzzle 4 [[9,5,1,5],[6,3,3,2],[9,1,5,1]])
          ([3, 5, 7, 13], [ ((1, 3), (1, 4)), ((2, 4), (3, 4)), ((2, 2), (1, 1)), ((2, 3), (3, 3)), ((2, 1), (1, 2)), ((3, 1), (3, 2))])
		,SimpleTest
          (Puzzle 4 [[4,9,2,8],[4,7,9,6],[7,6,3,3]])
          ([4, 7, 9, 11], [ ((1, 3), (1, 4)), ((3, 3), (3, 2)), ((1, 1), (2, 1)), ((2, 4), (3, 4)), ((2, 2), (3, 1)), ((1, 2), (2, 3))])
		,SimpleTest
          (Puzzle 5 [[9,8,2,6,8],[1,6,1,4,4],[4,2,1,2,9],[3,6,8,6,4]])
          ([3, 4, 6, 6, 16], [ ((2, 3), (1, 3)), ((3, 3), (4, 3)), ((2, 1), (1, 2)), ((2, 5), (1, 5)), ((3, 4), (4, 5)), 
		((3, 2), (3, 1)), ((1, 4), (2, 4)), ((4, 1), (4, 2)), ((3, 5), (4, 4)), ((1, 1), (2, 2))])
		,SimpleTest
          (Puzzle 5 [[4,6,9,8,4],[1,8,2,2,3],[8,7,1,6,4],[4,2,6,5,2]])
          ([3, 6, 7, 8, 12], [ ((2, 1), (3, 1)), ((2, 4), (3, 3)), ((4, 2), (4, 1)), ((2, 5), (3, 4)), ((3, 5), (4, 5)), 
		((1, 1), (2, 2)), ((3, 2), (2, 3)), ((4, 4), (4, 3)), ((1, 4), (1, 5)), ((1, 3), (1, 2))])
		,CountTest
          (Puzzle 3 [[8,2,6], [0,4,0]])
          2
		,CountTest
          (Puzzle 3 [[4,2,5], [0,4,5]])
          4
		,CountTest
          (Puzzle 4 [[5,2,8,6],[6,2,3,5],[9,4,8,2]])
          12
		,CountTest
          (Puzzle 4 [[2,2,9,8],[4,8,4,8],[1,2,1,4]])
          11
		,CountTest
          (Puzzle 5 [[4,4,4,4,4],[4,4,4,4,4],[5,5,5,5,5],[6,6,6,6,6]])
          0
		,CountTest
          (Puzzle 5 [[6,0,2,4,0],[0,6,4,3,2],[8,1,8,2,1],[6,3,1,4,0]])
          32
		,SimpleTest
          (Puzzle 6 [[2,6,4,2,3,2],[5,3,0,4,0,1],[1,0,2,7,0,7],[2,9,8,6,7,6],[0,2,8,3,3,2]])
          ([3, 4, 7, 8, 9, 10], [ ((2, 6), (1, 6)), ((4, 1), (3, 1)), ((1, 4), (2, 4)), ((5, 6), (4, 5)), ((1, 5), (2, 5)), 
		((5, 2), (5, 3)), ((2, 2), (1, 1)), ((5, 5), (4, 6)), ((1, 3), (2, 3)), ((2, 1), (1, 2)), ((4, 4), (5, 4))
		, ((3, 6), (3, 5)), ((3, 4), (3, 3)), ((4, 3), (3, 2)), ((4, 2), (5, 1))])
		,SimpleTest
          (Puzzle 6 [[2,8,8,7,2,1],[2,8,8,9,6,6],[4,7,2,9,6,4],[8,1,5,6,6,8],[2,8,4,4,1,1]])
          ([2,6,8,8,9,11],[((4,2),(5,1)),((1,6),(2,6)),((5,6),(4,5)),((5,5),(4,6)),((1,1),(2,1)),((3,1),(4,1)),((5,3),(5,2)),((4,3),(5,4)),((3,5),(4,4)),((2,5),(3,6)),((3,2),(3,3)),((2,2),(2,3)),((1,4),(1,5)),((1,2),(1,3)),((2,4),(3,4))])
		,SimpleTest
          (Puzzle 6 [[0,2,8,3,5,4],[4,3,6,4,6,0],[5,0,8,0,0,9],[0,6,7,5,7,5],[3,5,0,4,2,4]])
          ([5,6,7,8,9,10],[((2,2),(1,1)),((5,1),(5,2)),((1,6),(2,6)),((5,6),(4,6)),((3,1),(3,2)),((2,1),(1,2)),((2,4),(1,3)),((4,4),(5,4)),((4,2),(4,1)),((1,5),(2,5)),((2,3),(1,4)),((4,3),(5,3)),((4,5),(5,5)),((3,3),(3,4)),((3,6),(3,5))])

		,CountTest
          (Puzzle 6 [[2,6,4,2,3,2],[5,3,0,4,0,1],[1,0,2,7,0,7],[2,9,8,6,7,6],[0,2,4,3,3,2]])
          0
		,CountTest
          (Puzzle 6 [[2,1,4,2,5,3],[5,3,4,6,8,1],[1,0,0,2,3,5],[4,6,4,8,1,9],[3,8,2,7,4,2]])
          16
		,CountTest
          (Puzzle 6 [[9,4,3,2,2,6],[9,4,6,1,4,6],[2,7,4,8,4,2],[3,5,8,1,7,2],[3,4,2,3,8,8]])
          1536
		,SimpleTest
          (Puzzle 7 [[1,2,3,4,3,5,1],[3,7,6,8,0,8,2],[5,2,1,4,8,9,4],[3,6,5,2,5,0,6],[5,0,3,4,3,1,5],[1,2,0,5,3,4,2]])
          ([3,5,5,6,7,7,12],[((3,3),(4,3)),((5,6),(5,7)),((1,7),(2,6)),((6,2),(6,1)),((1,2),(1,1)),((1,3),(2,3)),((2,7),(1,6)),((1,5),(2,5)),((4,1),(5,1)),((2,1),(3,1)),((4,2),(5,2)),((5,3),(6,3)),((6,5),(6,4)),((5,5),(4,5)),((4,7),(4,6)),((5,4),(4,4)),((6,6),(6,7)),((2,2),(3,2)),((3,7),(3,6)),((3,5),(3,4)),((2,4),(1,4))])
	]

main :: IO ()
main = checkerMain solve tests


