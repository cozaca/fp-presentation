package me.fp.presentation.monad.impl

import arrow.Kind
import me.fp.presentation.applicative.Applicative
import me.fp.presentation.functor.impl.*
import me.fp.presentation.monad.Monad
import sun.text.normalizer.UTF16.append

class MonadInstances
{
    fun <A> maybeMonad(): Monad<ForMaybe, A> = object : Monad<ForMaybe, A> {
        override fun <B> ap(ff: Kind<ForMaybe, (A) -> B>, fa: MaybeOf<A>): Kind<ForMaybe, B> {
            return when(fa) {
                is Just -> when(ff) {
                    is Just -> pure(ff.a(fa.a))
                    is Empty -> ff
                    else -> Empty
                }
                is Empty -> fa
                else -> Empty
            }
        }

        override fun <X> pure(a: X): Kind<ForMaybe, X> {
            return Just(a)
        }

        override fun <A, B> flatMap(box: MaybeOf<A>, f: (A) -> MaybeOf<B>): Kind<ForMaybe, B> {
            return when(box) {
                is Just -> f(box.a)
                is Empty -> box
                else -> Empty
            }
        }
    }

    fun <A> listMonad(): Monad<ForMyList, A> = object : Monad<ForMyList, A> {
        override fun <B> ap(ff: Kind<ForMyList, (A) -> B>, fa: Kind<ForMyList, A>): Kind<ForMyList, B> {
            return when(fa) {
                is Cons -> when(ff) {
                    is Cons -> {
                        Cons(ff.head(fa.head), ap(ff, fa.tail))
                    }
                    is Nill -> Nill
                    else -> Nill
                }
                is Nill -> Nill
                else -> Nill
            }
        }

        override fun <X> pure(a: X): Kind<ForMyList, X>  = Cons(a, Nill)

        override fun <A, B> flatMap(box: Kind<ForMyList, A>, f: (A) -> Kind<ForMyList, B>): Kind<ForMyList, B> {
            return when(box) {
                is Cons -> append(f(box.head), flatMap(box.tail, f))
                is Nill -> Nill
                else -> Nill
            }
        }

        fun <A> append(head: MyListOf<A>, tail: MyListOf<A>): MyListOf<A> {
            return when(head) {
                is Cons -> Cons(head.head, tail)
                is Nill -> tail
                else -> Nill
            }
        }

    }
}