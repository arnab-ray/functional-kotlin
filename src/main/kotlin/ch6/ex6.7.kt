package ch6

import ch3.Cons
import ch3.List
import ch3.Nil
import ch3.foldRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise7 : WordSpec({

    fun <A> sequence(fs: List<Rand<A>>): Rand<List<A>> = { rng ->
        when (fs) {
            is Nil -> unit(List.empty<A>())(rng)
            is Cons -> {
                val (a, rng2) = fs.head(rng)
                val (b, rng3) = sequence(fs.tail)(rng2)
                Cons(a, b) to rng3
            }
        }
    }

    fun <A> sequence2(fs: List<Rand<A>>): Rand<List<A>> =
        foldRight(fs, unit(List.empty())) { f, acc ->
            map2(f, acc) { h, t -> Cons(h, t) }
        }

    fun ints2(count: Int, rng: RNG): Pair<List<Int>, RNG> {
        fun helper(c: Int): List<Rand<Int>> {
            return if (c == 0) {
                Nil
            } else {
                Cons({ r ->  1 to r }, helper(c - 1))
            }
        }

        return sequence(helper(count))(rng)
    }

    "sequence" should {

        "combine the results of many actions using recursion" {

            val combined: Rand<List<Int>> =
                sequence(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined(rng1).first shouldBe
                    List.of(1, 2, 3, 4)
        }

        """combine the results of many actions using
            foldRight and map2""" {

            val combined2: Rand<List<Int>> =
                sequence2(
                    List.of(
                        unit(1),
                        unit(2),
                        unit(3),
                        unit(4)
                    )
                )

            combined2(rng1).first shouldBe
                    List.of(1, 2, 3, 4)
        }
    }

    "ints" should {
        "generate a list of ints of a specified length" {
            ints2(4, rng1).first shouldBe
                    List.of(1, 1, 1, 1)
        }
    }
})