package ch2

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise3 : WordSpec({
    fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
        { a: A  -> { b: B -> f(a, b) } }

    "curry" should {
        """!break down a function that takes multiple arguments into
            a series of functions that each take only oneargument""" {

            val f: (Int) -> (Int) -> String =
                curry { a: Int, b: Int -> "$a:$b" }
            val y = f(1)(2)
            val z = f(1)(3)
            println(y)
            y shouldBe "1:2"
            z shouldBe "1:3"
        }
    }
})