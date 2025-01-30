package ch5

import ch3.List
import ch3.Nil
import ch5.Stream.Companion.cons
import ch5.Stream.Companion.empty
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A> Stream<A>.take(n: Int): Stream<A> {
    fun helper(xs: Stream<A>, a: Int): Stream<A> =
        when (xs) {
            is Empty -> empty()
            is Cons ->
                if (a == 0) empty()
                else cons(xs.head) { helper(xs.tail(), a - 1) }
        }

    return helper(this, n)
}


fun <A> Stream<A>.drop(n: Int): Stream<A> {
    fun helper(xs: Stream<A>, a: Int): Stream<A> =
        when (xs) {
            is Empty -> empty()
            is Cons ->
                if (a == 0) xs
                else helper(xs.tail(), a - 1)
        }

    return helper(this, n)
}

class Exercise2 : WordSpec({
    "Stream.take(n)" should {
        "return the first n elements of a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.take(3).toList() shouldBe List.of(1, 2, 3)
        }

        "return all the elements if the stream is exhausted" {
            val s = Stream.of(1, 2, 3)
            s.take(5).toList() shouldBe List.of(1, 2, 3)
        }

        "return an empty stream if the stream is empty" {
            val s = Stream.empty<Int>()
            s.take(3).toList() shouldBe Nil
        }
    }

    "Stream.drop(n)" should {
        "return the remaining elements of a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.drop(3).toList() shouldBe List.of(4, 5)
        }

        "return empty if the stream is exhausted" {
            val s = Stream.of(1, 2, 3)
            s.drop(5).toList() shouldBe Nil
        }

        "return empty if the stream is empty" {
            val s = Stream.empty<Int>()
            s.drop(5).toList() shouldBe Nil
        }
    }
})