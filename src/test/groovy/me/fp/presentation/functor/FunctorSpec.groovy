package me.fp.presentation.functor

import me.fp.presentation.functor.impl.Empty
import me.fp.presentation.functor.impl.FunctorInstances
import me.fp.presentation.functor.impl.Just
import spock.lang.Specification

class FunctorSpec extends Specification {
    def functor = new FunctorInstances().<Integer> maybeFunctor()

    def 'map maybe value with functor implementation'() {
        expect:
        functor.map(a, { value -> value.toString() }) == new Just("10")
        where:
        a << new Just(10)
    }

    def 'map on empty should return empty'() {
        expect:
        functor.map(a, { value -> value.toString() }) == a
        where:
        a << new Empty()
    }
}