module Test where

hohmann :: (Tick z) => Outp -> Dat -> Controller z (Outp)
hohmann out sollrad =
    do v_  <- getVLin out
       let (v@(vx,vy)) = normalize v_
       let rad1 = getRad out
           pos     = getPos out
           transtime = fromIntegral $ round $ hohmannTime rad1 sollrad :: Time
           force1 = - (hohmannSpeed1 rad1 sollrad)
           force2 = hohmannSpeed2 rad1 sollrad
           goalPoint = (normalize $ -pos) * (sollrad, sollrad)
           steuer1 = (force1 * vx, force1 * vy)
           steuer2 = (force2 * vx, force2 * vy)
       steuer out steuer1
       sequence_ $ replicate (transtime-1) noop
       outp <- noop -- scheitelpunkt
       steuer out steuer2
                  