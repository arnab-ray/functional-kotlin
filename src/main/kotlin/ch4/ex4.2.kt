package ch4

import ch3.*
import ch3.List
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlin.math.pow

private fun <A> length(xs: List<A>): Int = TODO()

private fun List<Double>.sum(): Double =
    foldLeft(this, 0.0) { x, y -> x + y }

private fun List<Double>.isEmpty(): Boolean =
    this == Nil

fun <A> List<A>.size(): Int = foldLeft(this, 0) { x, _ -> 1 + x }

fun <A, B> List<A>.map(f: (A) -> B): List<B> =
    foldRightL(this, List.empty()) { a, b -> Cons(f(a), b) }

fun mean(xs: List<Double>): Option<Double> =
    if (xs.isEmpty()) None
    else Some(xs.sum() / xs.size())

fun variance(xs: List<Double>): Option<Double> =
    mean(xs).flatMap { m -> mean(xs.map { x -> (x - m).pow(2) }) }

class Exercise2 : WordSpec({

    "variance" should {
        "determine the variance of a list of numbers" {
            val ls =
                List.of(1.0, 1.1, 1.0, 3.0, 0.9, 0.4)
            variance(ls).getOrElse { 0.0 } shouldBe
                    (0.675).plusOrMinus(0.005)
        }
    }
})