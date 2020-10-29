package me.fp.presentation.monoid.impl

import me.fp.presentation.monoid.Monoid

object MonoidInstances {

    val intMonoid: Monoid<Int> = object : Monoid<Int> {

        override fun compose(x: Int, y: Int): Int = x + y
        override fun identity(): Int = 0
    }

    val stringMonoid: Monoid<String> = object : Monoid<String> {
        override fun compose(x: String, y: String): String = "$x$y"

        override fun identity(): String = ""
    }
}