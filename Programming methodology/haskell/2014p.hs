foldr2 :: (a->b->c->c)->c->[a]->[b]->c
foldr2 _ c [] [] = c
foldr2 f c (x:xs) (y:ys) = f x y (foldr2 f c xs ys)

g :: Num a => a->a->a->a
g x y z = x + y + z
h :: Num a => a ->a->a
h x y = x+y

ziWith :: (a->b->c)->[a]->[b]->[c]
ziWith f = foldr2 g [] where
	g x y zs = (:) (f x y) zs

nieuj x
    | x >= 0 = True 
    | otherwise = undefined

data Tree a = Node a [Tree a]
inftree :: Tree Integer
inftree = head ts where
	ts = map(\ (Node n rs) -> (Node (n+1) (tail rs))) (Node 0 ts : ts)

f,s :: Tree a -> [a]
s (Node x xs) = map(\ (Node y _) -> y) xs
f (Node x xs) = x:f(head xs) 

data Expr a = Const a | Op (Expr a) (Expr a)

eval (Const c) f = c
eval (Op x y) f = eval x f `f` eval y f

f1 :: Integer -> Integer
f1 n = n * f1 (n-1)
f1 0 = 1 

f2::Integer -> Integer
f2 x = x
g2 x = \ y -> y
h2 x y = f2(g2 x y)

data Cos a = Cos a| Integer a deriving Show
newtype ToSamo a = ToSamo a deriving Show

levels :: [a] -> [[a]]
levels xs = l 1 xs

l :: Int -> [a] -> [[a]]
l n xs 
	|length xs == 0 = []
	|length xs < n = [xs]
	|otherwise = (take n xs):(l (2*n) (drop n xs))

v(m,n)
	| m == 0 = Nothing
	| m <= n = Just(m,(0,undefined))
	|otherwise = Just(n,(m-n,2*n))

class C a where
	var :: a -> a -> [a]
instance C Integer where
	var x y = [x,y]




