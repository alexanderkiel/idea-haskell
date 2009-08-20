module Test where

takeWhile1 f (x:xs) = if f x then x:takeWhile1 f xs else x:[head xs]