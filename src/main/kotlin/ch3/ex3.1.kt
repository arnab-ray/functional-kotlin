package ch3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

fun <A> tail(xs: List<A>): List<A> =
    when (xs) {
        is Nil -> throw IllegalStateException("Empty list")
        is Cons -> xs.tail
    }

class Exercise1 : WordSpec({
    "list ch3.tail" should {
        "return the the ch3.tail when present" {
            tail(List.of(1, 2, 3, 4, 5)) shouldBe
                    List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no ch3.tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }
})