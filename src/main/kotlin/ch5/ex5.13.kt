package ch5

import ch3.List
import ch3.Nil
import ch4.*
import ch5.Stream.Companion.empty
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> =
    unfold(this) { a ->
        when (a) {
            is Empty -> None
            is Cons -> Some(f(a.head()) to a.tail())
        }
    }

fun <A> Stream<A>.take2(n: Int): Stream<A> =
    unfold(this) { a ->
        when (a) {
            is Empty -> None
            is Cons -> if (n > 0) Some(a.head() to a.tail().take(n - 1)) else None
        }
    }

fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> =
    unfold(this) { a ->
        when (a) {
            is Empty -> None
            is Cons -> if (p(a.head())) Some(a.head() to a.tail().takeWhile(p)) else None
        }
    }

fun <A, B, C> Stream<A>.zipWith(
    that: Stream<B>,
    f: (A, B) -> C
): Stream<C> =
    unfold(this to that) { (a, b) ->
        when (a) {
            is Empty -> None
            is Cons -> when (b) {
                is Empty -> None
                is Cons -> Some(f(a.head(), b.head()) to (a.tail() to b.tail()))
            }
        }
    }

fun <A, B> Stream<A>.zipAll(
    that: Stream<B>
): Stream<Pair<Option<A>, Option<B>>> =
    unfold(this to that) { (a, b) ->
        when (a) {
            is Empty -> {
                when (b) {
                    is Empty -> None
                    is Cons -> Some((None to Some(b.head())) to (empty<A>() to b.tail()))
                }
            }
            is Cons -> {
                when (b) {
                    is Empty -> Some((Some(a.head()) to None) to (a.tail() to empty()))
                    is Cons -> Some((Some(a.head()) to Some(b.head())) to (a.tail() to b.tail()))
                }
            }
        }
    }

class Exercise13 : WordSpec({

    "Stream.map" should {
        "apply a function to each evaluated element in a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.map { "${(it * 2)}" }.toList() shouldBe
                    List.of("2", "4", "6", "8", "10")
        }
        "return an empty stream if no elements are found" {
            empty<Int>().map { (it * 2).toString() } shouldBe empty()
        }
    }

    "Stream.take(n)" should {
        "return the first n elements of a stream" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.take2(3).toList() shouldBe List.of(1, 2, 3)
        }

        "return all the elements if the stream is exhausted" {
            val s = Stream.of(1, 2, 3)
            s.take2(5).toList() shouldBe List.of(1, 2, 3)
        }

        "return an empty stream if the stream is empty" {
            val s = empty<Int>()
            s.take2(3).toList() shouldBe Nil
        }
    }

    "Stream.takeWhile" should {
        "return elements while the predicate evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { it < 4 }.toList() shouldBe List.of(1, 2, 3)
        }
        "return all elements if predicate always evaluates true" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { true }.toList() shouldBe
                    List.of(1, 2, 3, 4, 5)
        }
        "return empty if predicate always evaluates false" {
            val s = Stream.of(1, 2, 3, 4, 5)
            s.takeWhile { false }.toList() shouldBe List.empty()
        }
    }

    "Stream.zipWith" should {
        "apply a function to elements of two corresponding lists" {
            Stream.of(1, 2, 3)
                .zipWith(Stream.of(4, 5, 6)) { x, y -> x + y }
                .toList() shouldBe List.of(5, 7, 9)
        }
    }

    "Stream.zipAll" should {
        "combine two streams of equal length" {
            Stream.of(1, 2, 3).zipAll(Stream.of(1, 2, 3))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3)
            )
        }
        "combine two streams until the first is exhausted" {
            Stream.of(1, 2, 3, 4).zipAll(Stream.of(1, 2, 3))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3),
                Some(4) to None
            )
        }
        "combine two streams until the second is exhausted" {
            Stream.of(1, 2, 3).zipAll(Stream.of(1, 2, 3, 4))
                .toList() shouldBe List.of(
                Some(1) to Some(1),
                Some(2) to Some(2),
                Some(3) to Some(3),
                None to Some(4)
            )
        }
    }
})