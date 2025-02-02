package ch6

import ch3.Cons as ConsL
import ch3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise4 : WordSpec({

    fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        tailrec fun helper(c: Int, rng1: RNG, acc: List<Int>): Pair<List<Int>, RNG> {
            return if (c == 0) {
                acc to rng1
            } else {
                val (i, rng2) = rng1.nextInt()
                helper(c - 1, rng2, ConsL(i, acc))
            }
        }

        return helper(count, rng, List.empty())
    }

    "ints" should {
        "generate a list of ints of a specified length" {

            ints(5, rng1) shouldBe (List.of(1, 1, 1, 1, 1) to rng1)
        }
    }
})