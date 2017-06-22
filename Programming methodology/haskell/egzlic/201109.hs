sort :: [Int] -> [Int]
sort [] = []
sort (x:xs) = (sort [y| y <- xs, y <= x]) ++ [x] ++ (sort [y| y <- xs, y > x])

len :: [Int] -> Int
len [] = 0
len (x:xs) = 1 + len xs

setSize :: [Int] -> Int
setSize xs = len ( uniquelist (sort xs) )

uniquelist :: [Int] -> [Int]
uniquelist [] = []
uniquelist [x] = [x]
uniquelist (x:y:xs)
	| x == y = uniquelist (y:xs)
	| otherwise = [x] ++ uniquelist (y:xs)
			


