package me.fp.presentation

import me.fp.presentation.functor.impl.Cons
import me.fp.presentation.functor.impl.Just
import me.fp.presentation.functor.impl.Nill
import me.fp.presentation.monad.impl.MonadInstances
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class MonadSpek : Spek({

    given("flatMap maybe monad")
    {
        val maybeMonad = MonadInstances().maybeMonad<String>()
        val expected = maybeMonad.flatMap(Just("I"), { a: String -> Just(a + " used flatmap")})

        on("maybe flatmap result") {
            it("verify flatMap") {
                assertThat(expected).isEqualTo(Just("I used flatmap"))
            }
        }
    }

    given("list applicative")
    {
        val listMonad = MonadInstances().listMonad<Int>()
        on("identity law")
        {
            val mapper = listMonad.pure { a: Int -> a + 0 }
            val value = Cons(1, Cons(2, Cons(3, Nill)))
            val actual = listMonad.ap(mapper, value)

            it("law passed")
            {
                assertThat(actual).isEqualTo(value)
            }
        }
    }

    given("flatten maybe monad")
    {
        val maybeMonad = MonadInstances().maybeMonad<String>()
        val expected = maybeMonad.flatten(Just(Just("I am flattened")))

        on("maybe flatten result") {
            it("verify flatten") {
                assertThat(expected).isEqualTo(Just("I am flattened"))
            }
        }
    }


    given("monad laws")
    {
        val maybeMonad = MonadInstances().maybeMonad<Int>()
        on("identity law")
        {
            val mapper = { a: Int -> maybeMonad.pure(a + 0)}
            val actual = maybeMonad.flatMap(Just(10), mapper)

            it("law passed")
            {
                assertThat(actual).isEqualTo(Just(10))
            }
        }

        on("associativity law")
        {
            val a = { a: Int -> maybeMonad.pure(a * 2) }
            val b = { a: Int -> maybeMonad.pure(a * 3) }
            val c = { a: Int -> maybeMonad.pure(a * 4) }
            val value = Just(3)

            it("law passed")
            {
                val actualLeftHand = maybeMonad.flatMap(maybeMonad.flatMap(maybeMonad.flatMap(Just(10), a), b), c)
                val actualRightHand = maybeMonad.flatMap(maybeMonad.flatMap(maybeMonad.flatMap(Just(10), c), b), a)

                assertThat(actualLeftHand).isEqualTo(actualRightHand)
            }
        }
    }

    given("list monad")
    {
        val listMonad = MonadInstances().listMonad<Int>()
        on("identity law")
        {
            val mapper = { a: Int -> listMonad.pure(a + 0) }
            val value = Cons(1, Cons(2, Cons(3, Nill)))
            val actual = listMonad.flatMap(value, mapper)

            it("law passed")
            {
                assertThat(actual).isEqualTo(value)
            }
        }

        on("associativity law") {
            val elements = Cons(1, Cons(2, Nill))
            val a = { a: Int -> listMonad.pure(a * 2) }
            val b = { a: Int -> listMonad.pure(a * 3) }
            val c = { a: Int -> listMonad.pure(a * 4) }
            val actualLeftHand = listMonad.flatMap(listMonad.flatMap(listMonad.flatMap(elements, a), b), c)
            val actualRightHand = listMonad.flatMap(listMonad.flatMap(listMonad.flatMap(elements, c), b), a)

            it("law passed") {
                assertThat(actualLeftHand).isEqualTo(actualRightHand)
            }
        }
    }

})