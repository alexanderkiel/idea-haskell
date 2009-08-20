module Test where

-- shifts phi into [-pi,pi]
toPhiRange :: Dat -> Dat
toPhiRange phi | phi < -pi = phi + 2*pi
               | phi >  pi = phi - 2*pi
               | otherwise = phi
               