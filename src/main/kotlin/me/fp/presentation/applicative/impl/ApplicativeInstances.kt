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
                   is Just -> Just(ff.a(fa.a))
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
}