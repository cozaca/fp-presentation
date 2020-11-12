package me.fp.presentation.applicative.impl

import arrow.Kind
import me.fp.presentation.applicative.Applicative
import me.fp.presentation.functor.impl.ForMaybe
import me.fp.presentation.functor.impl.Just
import me.fp.presentation.functor.impl.Maybe
import me.fp.presentation.functor.impl.fix

class ApplicativeInstances
{
    fun <A> maybeApplicative(): Applicative<ForMaybe, A> = object : Applicative<ForMaybe, A> {
        override fun <B> ap(ff: Kind<ForMaybe, (A) -> B>, fa: Kind<ForMaybe, A>): Kind<ForMaybe, B> {
            return when(fa.fix()) {
               is Just -> Just()
            }
        }

    }
}