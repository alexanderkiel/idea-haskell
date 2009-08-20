module Test where

class Foo a => Bar a

class Tick z where
    tick :: Inp -> Controller z Outp
    getTime :: z -> Time

class Mem m where

    fromList :: [(Addr, Dat)] -> m
    toList   :: m -> [(Addr, Dat)]
    readMem  :: Addr -> m -> Dat
    writeMem :: Addr -> Dat -> m -> m
        