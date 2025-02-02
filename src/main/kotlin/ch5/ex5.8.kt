package ch5

import ch3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise8 : WordSpec({

    fun <A> constant(a: A): Stream<A> =
        Stream.cons({ a }, { constant(a) })

    "constants" should {
        "return an infinite stream of a given value" {
            constant(1).take(5).toList() shouldBe
                    List.of(1, 1, 1, 1, 1)
        }
    }
})