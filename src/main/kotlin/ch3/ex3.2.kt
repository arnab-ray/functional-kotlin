package ch3

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec

fun <A> setHead(xs: List<A>, x: A): List<A> =
    when (xs) {
        is Nil -> throw IllegalStateException("Empty list")
        is Cons -> Cons(x, xs.tail)
    }

class Exercise2 : WordSpec({
    "list setHead" should {
        "return a new List with a replaced head" {
            setHead(List.of(1, 2, 3, 4, 5), 6) shouldBe
                    List.of(6, 2, 3, 4, 5)
        }

        "throw an illegal state exception when no head is present" {
            shouldThrow<IllegalStateException> {
                setHead(Nil, 6)
            }
        }
    }
})