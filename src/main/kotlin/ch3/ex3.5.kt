package ch3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

fun <A> init(l: List<A>): List<A> =
    when (l) {
        is Nil -> throw IllegalStateException("")
        is Cons -> when (l.tail) {
            is Nil -> Nil
            is Cons -> Cons(l.head, init(l.tail))
        }
    }

class Exercise5 : WordSpec({

    "list init" should {
        "return all but the last element" {
            init(List.of(1, 2, 3, 4, 5)) shouldBe
                    List.of(1, 2, 3, 4)
        }

        "return Nil if only one element exists" {
            init(List.of(1)) shouldBe Nil
        }

        "throw an exception if no elements exist" {
            shouldThrow<IllegalStateException> {
                init(List.empty<Int>())
            }
        }
    }
})