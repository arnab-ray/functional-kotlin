package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> filter(xs: List<A>, f: (A) -> Boolean): List<A> =
    foldRightL(xs, List.empty()) { a, b -> if (f(a)) Cons(a, b) else b }

class Exercise18 : WordSpec({
    "list filter" should {
        "filter out elements not compliant to predicate" {
            val xs = List.of(1, 2, 3, 4, 5)
            filter(xs) { it % 2 == 0 } shouldBe List.of(2, 4)
        }
    }
})