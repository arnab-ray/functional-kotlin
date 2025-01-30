package ch5

import ch3.List
import ch3.Nil
import ch3.reverse
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import ch3.Cons as ConsL

fun <A> Stream<A>.toList(): List<A> {
    tailrec fun helper(xs: Stream<A>, acc: List<A>): List<A> =
        when (xs) {
            is Empty -> acc
            is Cons -> helper(xs.tail(), ConsL(xs.head(), acc))
        }
    return reverse(helper(this, Nil))
}

class Exercise1 : WordSpec({
    "Stream.toList" should {
        "force the stream into an evaluated list" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.toList() shouldBe List.of(1, 2, 3, 4, 5)
        }
    }
})