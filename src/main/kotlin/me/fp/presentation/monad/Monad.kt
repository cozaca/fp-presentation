package me.fp.presentation.monad

import arrow.Kind
import me.fp.presentation.applicative.Applicative

interface Monad<F, A>: Applicative<F, A> {
    fun <A, B> flatMap(box: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
    fun flatten(box: Kind<F, Kind<F, A>>): Kind<F, A> = flatMap(box, ::identity)
}