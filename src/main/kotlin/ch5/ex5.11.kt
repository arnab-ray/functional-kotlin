package ch5

import ch3.List
import ch4.*
import ch5.Stream.Companion.cons
import ch5.Stream.Companion.empty
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =
    f(z).map { (a, s) -> cons({ a }, { unfold(s, f) }) }.getOrElse { empty() }

class Exercise11 : WordSpec({
    "unfold" should {
        """return a stream based on an initial state and a function
            applied to each subsequent element""" {
            unfold(0) { s: Int ->
                Some(s to (s + 1))
            }.take(5).toList() shouldBe
                    List.of(0, 1, 2, 3, 4)
        }
    }
})