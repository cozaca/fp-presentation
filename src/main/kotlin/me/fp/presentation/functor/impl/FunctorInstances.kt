package me.fp.presentation.functor.impl

import me.fp.presentation.functor.Functor
import me.fp.presentation.functor.FunctorOf

sealed class Maybe<A>

data class Just<A>(val a: A) : Maybe<A>()
object Empty : Maybe<Nothing>()


object FunctorInstances {
/*    fun <A, B> maybeFunctor(): Functor<Maybe<A>, A> = object : Functor<Maybe<A>, A> {

        override fun <B> map(box: Maybe<A>, f: (A) -> B): Functor<Maybe<A>, B> {

            when(box) {
                Just<A>(a) -> Just(f(a))
                Empty -> Empty
            }
        }


*//*        override fun <B> map(box: FunctorOf<Maybe<A>>, f: (A) -> B): Functor<Maybe<A>, B> {
            when(box) {
                Just<A>(a) -> Just(f(a))
                Empty -> Empty
            }
        }*//*

  *//*      override fun <A, B> map(box: FunctorOf<A>, f: (A) -> B): Functor<Maybe<A>, B> {
            TODO("Not yet implemented")
        }*//*
    }*/

    class MaybeFInstance<A> : Functor<Maybe<A>, A> {

        override fun <B> map(box: Maybe<A>, f: (A) -> B): Maybe<B> {
           return when {
                box is Just -> Just(f(box.a))
                box is Empty -> Empty
            }
        }
    }


}