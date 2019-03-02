f1 :: [Int] -> [Int]
f1 [] = []
f1 [x] = [x]
f1 (x:y:xs)
	| x == y = f1 (x:xs)
	| x /= y = (x:y:xs) 

rev :: [Int] -> [Int] -> [Int]
rev acc [] = acc
rev acc (x:xs) = rev (x:acc) xs

f2 :: [Int] -> [Int]
f2 [] = []
f2 [x] = [x]
f2 list = rev [] (f1 ( rev [] (f1 list) ) ) 


