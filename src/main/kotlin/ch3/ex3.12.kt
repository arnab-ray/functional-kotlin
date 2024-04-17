package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B =
    foldRight(
        xs,
        { b: B -> b },
        { a, g -> { b -> g(f(b, a)) }}
    ) (z)

fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B =
    foldLeft(
        xs,
        { b: B -> b },
        { g, a -> { b -> g(f(a, b)) }}
    ) (z)

class Exercise12 : WordSpec({
    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(
                List.of(1, 2, 3, 4, 5),
                0
            ) { x, y -> x + y } shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(
                List.of(1, 2, 3, 4, 5),
                0
            ) { x, y -> x + y } shouldBe 15
        }
    }
})