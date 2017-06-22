bigdiv :: Integer -> Integer
bigdiv n
	|n == 0 = undefined
	|n == 1 = undefined
	|otherwise = last [x| x <- takeWhile (\x -> x <= n) primes, n `mod` x == 0]
	where primes = 2:[k| k <- [3..], all(\x -> k `mod` x /= 0) $ takeWhile(\x -> x*x <= k) primes] 
