package ch5

import ch4.None
import ch4.Option
import ch4.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise6 : WordSpec({
    fun <A> Stream<A>.headOption(): Option<A> =
        foldRight({ Option.empty() }, { a, _ -> Some(a) })

    "Stream.headOption" should {
        "return some first element from the stream if it is not empty" {
            val s = Stream.of(1, 2, 3, 4)
            s.headOption() shouldBe Some(1)
        }

        "return none if the stream is empty" {
            Stream.empty<Int>().headOption() shouldBe None
        }
    }
})