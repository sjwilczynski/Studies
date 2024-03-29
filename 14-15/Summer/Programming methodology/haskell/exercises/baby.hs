lucky :: (Integral a) => a -> String  
lucky 7 = "LUCKY NUMBER SEVEN!"  
lucky x = "Sorry, you're out of luck, pal!"  


factorial :: Integer -> Integer 
factorial 0 = 1  
factorial n = n * factorial (n - 1)  

-- class EEnum a where
-- fromEEnum :: a -> Int
-- toEEnum :: Int -> a

--instance (EEnum a,EEnum b) => EEnum (a,b) where
--fromEEnum (x,y) = fromEEnum(x) + fromEEnum(y)
--toEEnum i = (toEEnum(i),toEEnum(i))
pair :: (a->b,a->c) -> a -> (b,c)
pair (f,g) x = (f x, g x)

data Day = Mon|Tue|Wen|Thu|Fri|Sat|Sun deriving(Eq,Ord,Show,Enum)
workday :: Day -> Bool
workday = uncurry(&&).pair((Mon <=), (Fri >=))
generate :: Day ->[Day] --jak to zrobic zeby skrocic ta linijke
generate d = [a|a <- [Mon,Tue,Wen,Thu,Fri,Sat,Sun],workday a]
