package me.fp.presentation.applicative.impl

import arrow.Kind
import me.fp.presentation.applicative.Applicative
import me.fp.presentation.functor.impl.*

class ApplicativeInstances
{
    fun <A> maybeApplicative(): Applicative<ForMaybe, A> = object : Applicative<ForMaybe, A> {
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
    }

    fun <A> listApplicative(): Applicative<ForMyList, A> = object : Applicative<ForMyList, A> {
        override fun <B> ap(ff: Kind<ForMyList, (A) -> B>, fa: Kind<ForMyList, A>): Kind<ForMyList, B> {
            return when(fa) {
                is Cons -> when(ff) {
                    is Cons -> Cons(ff.head(fa.head), ap(ff.tail, fa.tail))
                    is Nill -> Nill
                    else -> Nill
                }
                is Nill -> Nill
                else -> Nill
            }
        }

        override fun <X> pure(a: X): Kind<ForMyList, X>  = Cons(a, Nill)

    }
}