import Data.Char
import Data.List

roots :: (Double,Double,Double) -> [Double]
roots (a,b,c) 
	| a == 0 = error"not quadratic"
	| e < 0 = error"complex roots"
	| otherwise = [(-b+r)/d,(-b-r)/d]
	where r = sqrt e
	      d = 2*a            --musza byc wciecia
	      e = b*b - 4*a*c 

data Roots = No
	|One Double
	|Two (Double,Double)
	deriving Show
roots1::(Double,Double,Double) -> Roots
roots1 (a,b,c)
	| a == 0 = error"Not quadratic"
	| e < 0 = No
	| e == 0  = One((-b-r)/d)
	| otherwise = Two(((-b-r)/d),((-b+r)/d))
	where r = sqrt e
	      d = 2*a
	      e = b*b - 4*a*c

integerToString::Integer -> String
integerToString  = (reverse.unfoldr(\i -> if i == 0 then Nothing
				else Just( (intToDigit.fromEnum) (i`mod`10),i `div` 10)))
fibba::[Integer]
fibba = 1:1:(zipWith(+) fibba $ tail fibba)

fib::Integer -> Integer
fib n = fibba !! (fromInteger n)

newtype FSet a = FSet (a->Bool)
empty :: FSet a
empty = FSet(const False)


				

