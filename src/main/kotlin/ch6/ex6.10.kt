package ch6

import ch3.Cons
import ch3.List
import ch3.foldRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

data class State<S, out A>(val run: (S) -> Pair<A, S>) {

    companion object {
        fun <S, A> unit(a: A): State<S, A> =
            State { s: S -> a to s }

        fun <S, A, B, C> map2(
            ra: State<S, A>,
            rb: State<S, B>,
            f: (A, B) -> C
        ): State<S, C> =
            ra.flatMap { a ->
                rb.map { b ->
                    f(a, b)
                }
            }

        fun <S, A> sequence(fs: List<State<S, A>>):
                State<S, List<A>> =
            foldRight(fs, unit(List.empty())) { f, acc ->
                map2(f, acc) { h, t -> Cons(h, t) }
            }
    }

    fun <B> map(f: (A) -> B): State<S, B> =
        State { s: S ->
            val (a, s1) = run(s)
            f(a) to s1
        }

    fun <B> flatMap(f: (A) -> State<S, B>): State<S, B> =
        State { s: S ->
            val (i, s2) = run(s)
            f(i).run(s2)
        }
}

class Exercise10 : WordSpec({
    "unit" should {
        "compose a new state of pure a" {
            State.unit<RNG, Int>(1).run(rng1) shouldBe (1 to rng1)
        }
    }
    "map" should {
        "transform a state" {
            State.unit<RNG, Int>(1)
                .map { it.toString() }
                .run(rng1) shouldBe ("1" to rng1)
        }
    }
    "flatMap" should {
        "transform a state" {
            State.unit<RNG, Int>(1)
                .flatMap { i ->
                    State.unit<RNG, String>(i.toString())
                }.run(rng1) shouldBe ("1" to rng1)
        }
    }
    "map2" should {
        "combine the results of two actions" {

            val combined: State<RNG, String> =
                State.map2(
                    State.unit(1.0),
                    State.unit(1)
                ) { d: Double, i: Int ->
                    ">>> $d double; $i int"
                }

            combined.run(rng1).first shouldBe ">>> 1.0 double; 1 int"
        }
    }
    "sequence" should {
        "combine the results of many actions" {

            val combined: State<RNG, List<Int>> =
                State.sequence(
                    List.of(
                        State.unit(1),
                        State.unit(2),
                        State.unit(3),
                        State.unit(4)
                    )
                )

            combined.run(rng1).first shouldBe List.of(1, 2, 3, 4)
        }
    }
})