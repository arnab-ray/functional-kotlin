package org.example.ch1

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentMapOf

class Exercise1 : WordSpec({
    fun fib(i: Int): Int {
        tailrec fun fibHelper(n: Int, a: Int, b: Int): Int {
            return if (n <= 1) {
                b
            } else {
                fibHelper(n - 1, b, a + b)
            }
        }
        return fibHelper(i, 0, 1)
    }

    "fib" should {
        "return the nth fibonacci number" {
            persistentMapOf(
                1 to 1,
                2 to 1,
                3 to 2,
                4 to 3,
                5 to 5,
                6 to 8,
                7 to 13,
                8 to 21
            ).forEach { (n, num) ->
                fib(n) shouldBe num
            }
        }
    }
})