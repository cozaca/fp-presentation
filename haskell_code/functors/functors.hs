-- | Functors

class MyFunctor f where
    m_fmap:: (a -> b) -> f a -> f b


data MyMaybe a = MyNothing | MyJust a deriving Show

instance MyFunctor MyMaybe where
    m_fmap _ MyNothing = MyNothing
    m_fmap f (MyJust x) = MyJust(f x)


data MyList a = Nil | MyCons a (MyList a) deriving (Show, Eq, Ord)

instance MyFunctor MyList where
    m_fmap _ Nil = Nil
    m_fmap f (MyCons x t)  = MyCons (f x) (m_fmap f t)


-- | Functors composition

maybeTail :: MyList a -> MyMaybe (MyList a)
maybeTail Nil = MyNothing
maybeTail (MyCons _ t) = MyJust t

-- | data for testing
myList = MyCons 1 (MyCons 2 (MyCons 3 Nil))

-- | Applicative functors

class MyApplicative f where
    m_pure :: a -> f a
    ap :: f (a -> b) -> f a -> f b

instance MyApplicative MyMaybe where
    m_pure  = MyJust
    ap (MyJust f) (MyJust a) = m_pure(f a)
    ap _ _ = MyNothing

mySum = ap (MyJust (+2)) (MyJust 3)

instance MyApplicative MyList where
    m_pure x = MyCons x Nil
    ap (MyCons hf fs) (MyCons hx xs) = MyCons (hf hx) (ap fs xs)
    ap _ _ = Nil

appFunToMyList = ap (MyCons (+1) (MyCons (*2) (MyCons (*3) Nil))) (MyCons 5 (MyCons 6 (MyCons 200 Nil)))

