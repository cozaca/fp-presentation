package me.fp.presentation.functor

import me.fp.presentation.functor.impl.Cons
import me.fp.presentation.functor.impl.Empty
import me.fp.presentation.functor.impl.FunctorInstances
import me.fp.presentation.functor.impl.Just
import me.fp.presentation.functor.impl.Nill
import spock.genesis.Gen
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class FunctorSpec extends Specification {
    def functor = new FunctorInstances().<Integer> maybeFunctor()
    def myListFunctor = new FunctorInstances().<Integer> myListFunctor()

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

    def 'maybe identity'() {
        expect:
        functor.map(F, { a  -> a} ) == F

        where:
        F << Gen.these(-50,50).then(Gen.integer).map {new Just<Integer>(it)}.take(100)
    }

    def 'maybe associativity'() {
        expect:
        functor.map(functor.map(F, {it * 5}), {it * 10}) == functor.map(functor.map(F, {it * 10}), {it * 5}) //
        functor.map(functor.map(F, {it * 5}), {it * 10}) == functor.map(F, {it * 5 * 10})

        where:
        F << Gen.these(-50,50).then(Gen.integer).map {new Just<Integer>(it)}.take(100)
    }

    def 'list identity'() {
        expect:
        myListFunctor.map(F, { a  -> a} ) == F

        where:
        F << Gen.these(-50,50).then(Gen.integer).map {new Cons<Integer>(it, new Cons<Integer>(it, new Nill()))}.take(100)
    }

    def 'list associativity'() {
        expect:
        functor.map(functor.map(F, {it * 5}), {it * 10}) == functor.map(functor.map(F, {it * 10}), {it * 5}) //
        functor.map(functor.map(F, {it * 5}), {it * 10}) == functor.map(F, {it * 5 * 10})

        where:
        F << Gen.these(-50,50).then(Gen.integer).map {new Cons<Integer>(it, new Cons<Integer>(it, new Nill()))}.take(100)
    }
}