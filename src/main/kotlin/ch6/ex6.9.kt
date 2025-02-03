package ch6

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise9 : WordSpec({

    fun <A, B> mapF(ra: Rand<A>, f: (A) -> B): Rand<B> =
        flatMap(ra) { i -> unit(f(i)) }

    "mapF" should {
        "map over a value using flatMap" {
            mapF(
                unit(1),
                { a -> a.toString() })(rng1).first shouldBe "1"
            mapF(
                unit(1),
                { a -> a.toDouble() })(rng1).first shouldBe 1.0
        }
    }

    fun <A, B, C> map2F(
        ra: Rand<A>,
        rb: Rand<B>,
        f: (A, B) -> C
    ): Rand<C> =
        flatMap(ra) { i -> map(rb) { j -> f(i, j) } }

    "map2F" should {
        "combine the results of two actions" {

            val combined: Rand<String> =
                map2F(
                    unit(1.0),
                    unit(1),
                    { d, i -> ">>> $d double; $i int" })

            combined(rng1).first shouldBe ">>> 1.0 double; 1 int"
        }
    }
})