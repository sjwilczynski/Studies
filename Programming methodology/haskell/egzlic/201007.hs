allMult:: Int -> [[Int]]
allMult 1 = [[1],[-1]]
allMult n = [(1:xs)| xs <- (allMult (n-1))] ++ [(-1:xs)| xs <- (allMult (n-1))]

dot :: [Int] -> [Int] -> Int 
dot xs [] = 0
dot [] xs = 0
dot (x:xs) (y:ys) = x*y + (dot xs ys)

good :: [Int] -> Int -> Bool
good xs k = foldl (||) False [( (dot xs ys) == k)| ys <- allMult (length xs)]
