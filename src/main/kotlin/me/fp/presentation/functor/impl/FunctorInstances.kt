package me.fp.presentation.functor.impl

import me.fp.presentation.functor.Functor

class ForMaybe private constructor() {
    companion object
}
typealias MaybeOf<A> = arrow.Kind<ForMaybe, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> MaybeOf<A>.fix(): Maybe<A> =
        this as Maybe<A>

sealed class Maybe<A> : MaybeOf<A>
data class Just<A>(val a: A) : Maybe<A>()
object Empty : Maybe<Nothing>()


class FunctorInstances {
    fun <A> maybeFunctor(): Functor<ForMaybe, A> = object : Functor<ForMaybe, A> {
        override fun <A, B> map(box: MaybeOf<A>, f: (A) -> B): MaybeOf<B> {
            return when {
                box is Just -> Just(f(box.a))
                box is Empty -> box
                else -> Empty
            }
        }
    }
}