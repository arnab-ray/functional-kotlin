package ch5

import ch3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise10 : WordSpec({

    fun fibs(): Stream<Int> {
        fun helper(a: Int, b: Int): Stream<Int> =
            Stream.cons({ a }, { helper(b, a + b) })

        return helper(0, 1)
    }

    "fibs" should {
        "return a Stream of fibonacci sequence numbers" {
            fibs().take(10).toList() shouldBe
                    List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
        }
    }
})