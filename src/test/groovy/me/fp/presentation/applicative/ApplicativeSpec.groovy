package me.fp.presentation.applicative

import kotlin.Function
import me.fp.presentation.applicative.impl.ApplicativeInstances
import me.fp.presentation.functor.impl.Just
import me.fp.presentation.functor.impl.Maybe
import spock.lang.Specification

class ApplicativeSpec extends Specification {

    def applicative = new ApplicativeInstances().<String>maybeApplicative()

    def 'map maybe using applicative'() {
        expect:
        applicative.map(a, {v -> v + "here"}) == new Just("Add here")
        where:
        a << new Just("Add ")
    }

    def 'apply function on maybe using applicative'() {
        expect:
        Maybe<Function<String, String>> ff = applicative.<Function<String, String>>pure({ String a -> a + "here"})
        applicative.ap(ff, a) == new Just("Add here")
        where:
        a << new Just("Add ")
    }


}
