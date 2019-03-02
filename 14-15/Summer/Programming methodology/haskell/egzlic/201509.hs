f1 :: [Int] -> (Int, Int)
f1 list = (length([x| x <- list, x `mod` 2 == 0]), length([x| x <- list, x `mod` 2 == 1]))
	
f2 :: [Int] -> (Int, Int)
f2 [] = (0,0)
f2 (x:xs)
	| x `mod` 2 == 0 = ( fst (f2 xs) + 1, snd (f2 xs) )
	| x `mod` 2 == 1 = ( fst (f2 xs), snd (f2 xs) + 1)
