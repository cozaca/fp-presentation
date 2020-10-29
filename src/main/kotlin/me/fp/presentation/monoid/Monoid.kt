package me.fp.presentation.monoid

/**
 * category with 1 object = Monoid (not a mathematical definition)
 * if is a category we need to follow the composition, associativity and identity rules
 * */
interface Monoid<A> {

    fun compose(x: A, y: A): A
    fun identity(): A
}