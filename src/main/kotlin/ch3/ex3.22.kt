package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> zipWith(xa: List<A>, xb: List<A>, f: (A, A) -> A): List<A> =
    when (xa) {
        is Nil -> Nil
        is Cons -> when (xb) {
            is Nil -> Nil
            is Cons -> Cons(f(xa.head, xb.head), zipWith(xa.tail, xb.tail, f))
        }
    }

class Exercise22 : WordSpec({
    "list zipWith" should {
        "apply a function to elements of two corresponding lists" {
            zipWith(
                List.of(1, 2, 3),
                List.of(4, 5, 6)
            ) { x, y -> x + y } shouldBe List.of(5, 7, 9)
        }
    }
})