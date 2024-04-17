package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> length(xs: List<A>): Int =
    when (xs) {
        is Nil -> 0
        is Cons -> 1 + length(xs.tail)
    }

class Exercise8 : WordSpec({
    "list length" should {
        "calculate the length" {
            length(List.of(1, 2, 3, 4, 5)) shouldBe 5
        }

        "calculate zero for an empty list" {
            length(Nil) shouldBe 0
        }
    }
})