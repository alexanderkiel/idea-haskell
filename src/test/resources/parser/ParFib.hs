module Main where

import Control.Parallel (par, pseq)
import GHC.Conc (numCapabilities)

main :: IO ()
main = do
    putStrLn $ "#number of cores: " ++ show numCapabilities

    -- Runs 100 instances of fib 30 in parallel
    print $ parMap fib $ take 100 $ map (\x -> 30) [0..]

-- The usual fib function which return the n-th fibonacci number
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)

-- Runs all function applications f x in parallel
-- and concatenates the resulting list afterwards
parMap :: (a -> b) -> [a] -> [b]
parMap f []     = []
parMap f (x:xs) = y `par` y:ys where
    y  = f x
    ys = parMap f xs
