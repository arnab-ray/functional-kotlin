package ch5

import ch3.List
import ch4.None
import ch4.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import ch3.Cons as ConsL
import ch3.Nil as NilL

class Exercise15 : WordSpec({
    fun <A> Stream<A>.tails(): Stream<Stream<A>> =
        unfold(this) { a ->
            when (a) {
                is Empty -> None
                is Cons -> Some(a to a.tail())
            }
        }

    fun <A, B> List<A>.map(f: (A) -> B): List<B> =
        when (this) {
            is NilL -> NilL
            is ConsL -> ConsL(f(this.head), this.tail.map(f))
        }

    "Stream.tails" should {
        "return the stream of suffixes of the input sequence" {
            Stream.of(1, 2, 3)
                .tails()
                .toList()
                .map { it.toList() } shouldBe
                    List.of(
                        ConsL(1, ConsL(2, ConsL(3, NilL))),
                        ConsL(2, ConsL(3, NilL)),
                        ConsL(3, NilL)
                    )
        }
    }
})