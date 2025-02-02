package ch5

import ch3.List
import ch5.Stream.Companion.cons
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise16 : WordSpec({

    fun <A, B> Stream<A>.scanRight(z: B, f: (A, () -> B) -> B): Stream<B> =
        foldRight(
            { z to Stream.of(z) },
            { a: A, p0: () -> Pair<B, Stream<B>> ->
                val p1: Pair<B, Stream<B>> by lazy { p0() }
                val b2: B = f(a) { p1.first }
                Pair(b2, cons({ b2 }, { p1.second }))
            }
        ).second

    "Stream.scanRight" should {
        "behave like foldRight" {
            Stream.of(1, 2, 3)
                .scanRight(0, { a, b ->
                    a + b()
                }).toList() shouldBe List.of(6, 5, 3, 0)
        }
    }
})