package ch5

import ch3.List
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise9 : WordSpec({

    fun from(n: Int): Stream<Int> =
        Stream.cons({ n }, { from(n + 1) })

    "from" should {
        "return a Stream of ever incrementing numbers" {
            from(5).take(5).toList() shouldBe
                    List.of(5, 6, 7, 8, 9)
        }
    }
})