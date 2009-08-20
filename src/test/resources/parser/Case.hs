module Test where

typeShow addr = case snd (instr vm !! addr) of
                            DType t _ _ -> typeD t
                            SType t _ -> typeS t

a = case (isFinished out) of
               True  -> putStrLn $"Finished with score " ++ (show $ score out)
               False -> helper vm'

b = case args !! 0 of
    "full" -> fullNetworkAnalysis args
    "dep" -> dependencyAnalysis args
    "plot" -> gnuplotter_wrapper args
    otherwise -> fail "\nUsage: See Source.\n"

c = case op of
             0 -> SType Noop 0
             1 -> SType (Cmpz (loadIMM imm)) r1
             2 -> SType Sqrt r1
             3 -> SType Copy r1
             4 -> SType Input r1
             _ -> error $ "Wrong SType " ++ (show op)