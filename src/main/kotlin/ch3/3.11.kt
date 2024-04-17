package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> reverse(xs: List<A>): List<A> =
    foldLeft(xs, List.empty()) { x, y -> Cons(y, x) }


class Exercise11 : WordSpec({
    "list reverse" should {
        "reverse list elements" {
            reverse(List.of(1, 2, 3, 4, 5)) shouldBe
                    List.of(5, 4, 3, 2, 1)
        }
    }
})