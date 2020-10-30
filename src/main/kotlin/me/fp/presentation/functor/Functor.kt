package me.fp.presentation.functor

import arrow.Kind

interface Functor<F, A> {
    fun <A, B> map(box: Kind<F, A>, f: (A) -> B): Kind<F, B>
}