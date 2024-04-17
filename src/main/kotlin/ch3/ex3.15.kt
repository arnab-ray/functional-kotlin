package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun increment(xs: List<Int>): List<Int> =
    foldRightL(xs, List.empty()) { a: Int, b -> Cons(a + 1, b) }

class Exercise15 : WordSpec({
    "list increment" should {
        "1add 1 to every element" {
            increment(List.of(1, 2, 3, 4, 5)) shouldBe
                    List.of(2, 3, 4, 5, 6)
        }
    }
})