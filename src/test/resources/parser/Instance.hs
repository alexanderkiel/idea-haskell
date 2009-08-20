module Test where

instance Num Int  where     -- simplified instance of Num Int
    x + y       =  addInt x y
    negate x    =  negateInt x
  
instance Num Float  where   -- simplified instance of Num Float
    x + y       =  addFloat x y
    negate x    =  negateFloat x

instance (Eq a, Show a) => Foo [a]

instance Num a => Bar [a]

instance Mem (Array Addr Dat) where

    fromList m = array (fst $ head m, fst $ last m) m
    toList     = assocs
    readMem    = flip (!)
    writeMem addr dat m = m // [(addr, dat)]

instance Show NameSpace where
    show (NInp x) = "NInp" ++ show x
    show (NOutp x) = "NOutp" ++ show x
    show (NMem x) = "NMem_" ++ show x

instance Tick VM where
    tick inp = do vm <- get
                  let (vm', outp) = oneRun inp vm
                  put vm'
                  tell $! DL.singleton $ Trace1 (time vm) inp outp
                  return outp
    getTime (VM {time =t}) = t

instance Binary Header where
    put (Header team scenario) = do
                         put (reverseWord32 0xCAFEBABE)
                         put $ reverseWord32 (fromIntegral $ team)
                         put $ reverseWord32 (fromIntegral $ scenario)
    get = return $ Header 0 0

instance Binary Frame where
    put (Frame { step = s, vals = vs}) = do
                        put (reverseWord32 $ fromIntegral $ s)
                        put (reverseWord32 $ fromIntegral $ (length vs))
                        mapM_ put $ vs
    get = return $ Frame {step = 0 , vals = []}

instance Binary MPair where
    put (MPair (a,d)) = do
                        put (reverseWord32 $ fromIntegral $ a)
                        put (reverseWord64 $ doubleToWord64 $ d)
    get = return $ MPair (0,0)

instance Show (Dat -> Dat -> Bool) where
    show = const "<cmp-fun>"

instance Num a => Num (a,a) where
    (+) (a,c) (b,d) = (a+b,c+d)
    (-) (a,c) (b,d) = (a-b,c-d)
    (*) (a,c) (b,d) = (a*b,c*d)
    negate (a,b)    = (-a,-b)
    abs (a,b)       = (abs(a),abs(b))
    signum (a,b)    = (signum a, signum b)
    fromInteger i   = (fromInteger i, fromInteger i)
                        

    