split :: [a] -> ([a],[a])

split [] = ([],[])
split [x] = ([x],[])
split (x:y:xs) = ( (x:(fst.split) xs), (y:(snd.split) xs) )
