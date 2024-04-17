package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun doubleToString(xs: List<Double>): List<String> =
    foldRightL(xs, List.empty()) { a: Double, b -> Cons(a.toString(), b) }

class Exercise16 : WordSpec({
    "list doubleToString" should {
        "convert every double element to a string" {
            doubleToString(List.of(1.1, 1.2, 1.3, 1.4)) shouldBe
                    List.of("1.1", "1.2", "1.3", "1.4")
        }
    }
})