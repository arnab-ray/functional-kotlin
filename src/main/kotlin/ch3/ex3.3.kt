package ch3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

fun <A> drop(l: List<A>, n: Int): List<A> =
    if (n == 0) {
        l
    } else {
        when (l) {
            is Nil -> throw IllegalStateException("")
            is Cons -> drop(l.tail, n - 1)
        }
    }

class Exercise3 : WordSpec({
    "list ch3.drop" should {
        "ch3.drop a given number of elements within capacity" {
            drop(List.of(1, 2, 3, 4, 5), 3) shouldBe
                    List.of(4, 5)
        }

        "ch3.drop a given number of elements up to capacity" {
            drop(List.of(1, 2, 3, 4, 5), 5) shouldBe Nil
        }

        """throw an illegal state exception when dropped elements
            exceed capacity""" {
            shouldThrow<IllegalStateException> {
                drop(List.of(1, 2, 3, 4, 5), 6)
            }
        }
    }
})