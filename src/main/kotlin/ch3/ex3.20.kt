package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> filter2(xa: List<A>, f: (A) -> Boolean): List<A> =
    flatMap(xa) { a -> if (f(a)) List.of(a) else List.empty() }

class Exercise20 : WordSpec({
    "list filter" should {
        "filter out elements not compliant to predicate" {
            filter2(
                List.of(1, 2, 3, 4, 5)
            ) { it % 2 == 0 } shouldBe List.of(2, 4)
        }
    }
})

fun main() {
    println(
        filter2(
            List.of(1, 2, 3, 4, 5)
        ) { it % 2 == 0 }
    )
}