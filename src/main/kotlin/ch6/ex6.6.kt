package ch6

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise6 : WordSpec({

    fun <A, B, C> map2(
        ra: Rand<A>,
        rb: Rand<B>,
        f: (A, B) -> C
    ): Rand<C> =
        { rng ->
            val (a, rng2) = ra(rng)
            val (b, rng3) = rb(rng2)
            f(a, b) to rng3
        }

    "map2" should {
        "combine the results of two actions" {

            val combined: Rand<String> =
                map2(
                    unit(1.0),
                    unit(1), { d, i ->
                        ">>> $d double; $i int"
                    })

            combined(rng1).first shouldBe ">>> 1.0 double; 1 int"
        }
    }
})