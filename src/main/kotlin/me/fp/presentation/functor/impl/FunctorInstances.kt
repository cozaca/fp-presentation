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


class ForMyList private constructor() {
    companion object
}
typealias MyListOf<A> = arrow.Kind<ForMyList, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> MyListOf<A>.fix(): MyList<A> =
        this as MyList<A>

sealed class MyList<A> : MyListOf<A>
data class Cons<A>(val head: A, val tail: MyListOf<A>) : MyListOf<A>
object Nill : MyListOf<Nothing>


class FunctorInstances {

    fun <A> maybeFunctor(): Functor<ForMaybe, A> = object : Functor<ForMaybe, A> {
        override fun <B> map(box: MaybeOf<A>, f: (A) -> B): MaybeOf<B> {
            return when (box) {
                is Just -> Just(f(box.a))
                is Empty -> box
                else -> Empty
            }
        }
    }

    fun <A> myListFunctor(): Functor<ForMyList, A> = object : Functor<ForMyList, A> {
        override fun <B> map(box: MyListOf<A>, f: (A) -> B): MyListOf<B> {
            return when (box) {
                is Cons -> Cons(f(box.head), map(box.tail, f))
                is Nill -> box
                else -> Nill
            }
        }
    }

}