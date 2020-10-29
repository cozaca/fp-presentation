package me.fp.presentation.functor

import arrow.Kind


interface Functor<F> {

    fun <B> map(box: F, f: (A) -> B): Functor<F>
}

