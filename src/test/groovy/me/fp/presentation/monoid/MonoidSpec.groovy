package me.fp.presentation.monoid


import spock.genesis.Gen
import spock.lang.Specification
import spock.lang.Unroll

import static me.fp.presentation.monoid.impl.MonoidInstances.intMonoid
import static me.fp.presentation.monoid.impl.MonoidInstances.stringMonoid

@Unroll
class MonoidSpec extends Specification {

    def 'intMonoid: right identity: given #value compose with id = #value'() {
        expect:
        intMonoid.compose(a, intMonoid.identity()) == a

        where:
        a << Gen.these(-50, 50).then(Gen.integer).take(100)
    }

    def 'intMonoid:left identity: given #value compose with id = #value'() {
        expect:
        intMonoid.compose(intMonoid.identity(), a) == a

        where:
        a << Gen.these(-50, 50).then(Gen.integer).take(100)
    }

    def 'intMonoid:composition'() {
        expect:
        intMonoid.compose(first, second) == first + second

        where:
        first << Gen.these(-50, 50).then(Gen.integer).take(100)
        second << Gen.these(-50, 50).then(Gen.integer).take(100)
    }

    def 'intMonoid:associativity'() {
        expect:
        intMonoid.compose(intMonoid.compose(a, b), c) == intMonoid.compose(a, intMonoid.compose(b, c))

        where:
        a << Gen.these(-50, 50).then(Gen.integer).take(100)
        b << Gen.these(-50, 50).then(Gen.integer).take(100)
        c << Gen.these(-50, 50).then(Gen.integer).take(100)
    }

    def 'stringMonoid: right identity: given #value compose with id = #value'() {
        expect:
        stringMonoid.compose(a, stringMonoid.identity()) == a

        where:
        a << Gen.these("", "foo").then(Gen.string).take(100)
    }

    def 'stringMonoid:left identity: given #value compose with id = #value'() {
        expect:
        stringMonoid.compose(stringMonoid.identity(), a) == a

        where:
        a << Gen.these("", "foo").then(Gen.string).take(100)
    }

    def 'stringMonoid:composition'() {
        expect:
        stringMonoid.compose(first, second) == "$first$second"

        where:
        first << Gen.these("", "foo").then(Gen.string).take(100)
        second << Gen.these("", "foo").then(Gen.string).take(100)
    }

    def 'stringMonoid:associativity'() {
        expect:
        stringMonoid.compose(stringMonoid.compose(a, b), c) == stringMonoid.compose(a, stringMonoid.compose(b, c))

        where:
        a << Gen.these("", "foo").then(Gen.string).take(100)
        b << Gen.these("", "foo").then(Gen.string).take(100)
        c << Gen.these("", "foo").then(Gen.string).take(100)
    }
}