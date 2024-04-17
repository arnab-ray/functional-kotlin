package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> concat(lla: List<List<A>>): List<A> =
    foldRightL(lla, List.empty()) { a, b -> append(a, b) }

fun <A> concat2(lla: List<List<A>>): List<A> =
    foldRightL(lla, List.empty()) {
        a: List<A>, b: List<A> -> foldRightL(a, b) { x, y -> Cons(x, y) }
    }

class Exercise14 : WordSpec({
    "list concat" should {
        "concatenate a list of lists into a single list" {
            concat(
                List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6)
                )
            ) shouldBe List.of(1, 2, 3, 4, 5, 6)

            concat2(
                List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6)
                )
            ) shouldBe List.of(1, 2, 3, 4, 5, 6)
        }
    }
})

fun main() {
    println(
        concat2(
            List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            )
        )
    )
}