package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B> flatMap(xa: List<A>, f: (A) -> List<B>): List<B> =
    foldRightL(xa, List.empty()) { a, b -> append(f(a), b) }

class Exercise19 : WordSpec({
    "list flatmap" should {
        "map and flatten a list" {
            val xs = List.of(1, 2, 3)
            flatMap(xs) { i -> List.of(i, i) } shouldBe
                    List.of(1, 1, 2, 2, 3, 3)
        }
    }
})