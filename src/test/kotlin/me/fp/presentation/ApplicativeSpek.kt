package me.fp.presentation

import me.fp.presentation.applicative.impl.ApplicativeInstances
import me.fp.presentation.functor.impl.Cons
import me.fp.presentation.functor.impl.Nill
import me.fp.presentation.functor.impl.Just
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class ApplicativeSpek : Spek({
    given("list applicative")
    {
        val listApplicative = ApplicativeInstances().listApplicative<Int>()
        on("identity law")
        {
            val mapper = listApplicative.pure { a: Int -> a + 0}
            val value = Cons(1, Cons(2, Cons(3, Nill)))
            val actual = listApplicative.ap(mapper, value)

            it("law passed")
            {
                assertThat(actual).isEqualTo(value)
            }
        }

        on("associativity law") {
            val elements = Cons(1, Cons(2, Nill))
            val a = listApplicative.pure { a: Int -> a * 2 }
            val b = listApplicative.pure { a: Int -> a * 3 }
            val c = listApplicative.pure { a: Int -> a * 4 }
            val actualLeftHand = listApplicative.ap(a, listApplicative.ap(b, listApplicative.ap(c, elements)))
            val actualRightHand = listApplicative.ap(c, listApplicative.ap(a, listApplicative.ap(b, elements)))

            it("law passed") {
                assertThat(actualLeftHand).isEqualTo(actualRightHand)
            }
        }

        on("map list") {
            val actual = listApplicative.map(Cons(1, Cons(2, Nill))) { v -> v + 2 }
            it("verify mapping") {
                assertThat(actual).isEqualTo(Cons(3, Cons(4,Nill)))
            }
        }
    }

    given("maybe applicative")
    {
        val maybeApplicative = ApplicativeInstances().maybeApplicative<String>()
        on("identity law")
        {
            val mapper = maybeApplicative.pure { a: String -> a + "" }
            val value = Just("Hello")
            val actual = maybeApplicative.ap(mapper, value)

            it("law passed")
            {
                assertThat(actual).isEqualTo(value)
            }
        }

        on("associativity law")
        {
            val maybeApplicative = ApplicativeInstances().maybeApplicative<Int>()
            val a = maybeApplicative.pure { a: Int -> a * 2 }
            val b = maybeApplicative.pure { a: Int -> a * 3 }
            val c = maybeApplicative.pure { a: Int -> a * 4 }
            val value = Just(3)

            it("law passed")
            {
                val actualLeftHand = maybeApplicative.ap(a, maybeApplicative.ap(b, maybeApplicative.ap(c, value)))
                val actualRightHand = maybeApplicative.ap(c, maybeApplicative.ap(b, maybeApplicative.ap(a, value)))

                assertThat(actualLeftHand).isEqualTo(actualRightHand)
            }
        }

        on("map maybe") {
            val actual = maybeApplicative.map(Just("Add ")) { v -> v + "here" }
            it("verify mapping") {
                assertThat(actual).isEqualTo(Just("Add here"))
            }
        }

    }

})