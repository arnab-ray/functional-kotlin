package ch7

import io.kotlintest.specs.WordSpec

class Par2<A>(val get: A) {
    companion object {

        fun <A> unit(a: A): Par2<A> = Par2(a)

        fun <A, B, C> map2(
            a1: Par2<A>,
            a2: Par2<B>,
            f: (A, B) -> C
        ): Par2<C> = Par2(f(a1.get, a2.get))

        fun <A> fork(a: () -> Par2<A>): Par2<A> = a()

        fun <A> lazyUnit(a: () -> A): Par2<A> = Par2(a())

        fun <A> run(a: Par2<A>): A = a.get
    }
}

class Solution2 : WordSpec({

    "Par" should {
        "create a computation that immediately results in a value" {
            Par2.unit { 1 }
        }
        """combine the results of two parallel computations with
            a binary function""" {
            Par2.map2(
                Par2.unit(1),
                Par2.unit(2)
            ) { i: Int, j: Int -> i + j }
        }
        "mark a computation for concurrent evaluation by run" {
            Par2.fork { Par2.unit { 1 } }
        }
        "wrap expression a for concurrent evaluation by run" {
            Par2.lazyUnit { 1 }
        }
        """fully evaluate a given Par spawning computations
            and extracting value""" {
            Par2.run(Par2.unit(1))
        }
    }
})