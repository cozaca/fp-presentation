package me.fp.presentation.functor

import arrow.Kind

interface Functor<F, A> {
    fun <A> identity(a: A): A = a
    fun <B> map(box: Kind<F, A>, f: (A) -> B): Kind<F, B>
}