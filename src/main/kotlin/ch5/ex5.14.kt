package ch5

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise14 : WordSpec({

    fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean =
        this.zipAll(that)
            .takeWhile { !it.second.isEmpty() }
            .forAll { it.first == it.second }

    "Stream.startsWith" should {
        "detect if one stream is a prefix of another" {
            Stream.of(1, 2, 3).startsWith(
                Stream.of(1, 2)
            ) shouldBe true
        }
        "detect if one stream is not a prefix of another" {
            Stream.of(1, 2, 3).startsWith(
                Stream.of(2, 3)
            ) shouldBe false
        }
    }
})