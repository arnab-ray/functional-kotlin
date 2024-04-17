package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun add(xa: List<Int>, xb: List<Int>): List<Int> =
    when (xa) {
        is Nil -> Nil
        is Cons -> when (xb) {
            is Nil -> Nil
            is Cons -> Cons(xa.head + xb.head, add(xa.tail, xb.tail))
        }
    }

class Exercise21 : WordSpec({
    "list add" should {
        "add elements of two corresponding lists" {
            add(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe
                    List.of(5, 7, 9)
        }

        "add elements of an empty and non-empty list" {
            add(List.empty(), List.of(4, 5, 6)) shouldBe
                    List.empty()
        }
    }
})