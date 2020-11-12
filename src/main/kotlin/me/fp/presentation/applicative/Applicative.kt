package me.fp.presentation.applicative

import arrow.Kind
import me.fp.presentation.functor.Functor

interface Applicative<F, A>: Functor<F, A> {

    fun <B> ap(ff: Kind<F, (A) -> B>, fa: Kind<F, A>): Kind<F, B>

    fun <X> pure(a: X): Kind<F, X>

    override fun <B> map(box: Kind<F,A>, f: (A) -> B): Kind<F, B> {
        return ap(pure(f), box)
    }
}