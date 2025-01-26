package ch4

import ch3.Cons
import ch3.List
import ch3.Nil
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> sequence(xs: List<Option<A>>): Option<List<A>> =
    xs.foldRight(Some(Nil)) { a: Option<A>, b: Option<List<A>> ->
        map2(a, b) { a1: A, a2: List<A> -> Cons(a1, a2) }
    }

class Exercise4 : WordSpec({

    "sequence" should {
        "turn a list of some options into an option of list" {
            val lo =
                List.of(Some(10), Some(20), Some(30))
            sequence(lo) shouldBe Some(List.of(10, 20, 30))
        }
        "turn a list of options containing none into a none" {
            val lo =
                List.of(Some(10), None, Some(30))
            sequence(lo) shouldBe None
        }
    }
})