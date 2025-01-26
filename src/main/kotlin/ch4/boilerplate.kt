package ch4

import ch3.Cons
import ch3.List
import ch3.Nil

fun <A, B> List<A>.map(f: (A) -> B): List<B> =
    this.foldRight(
        List.empty()
    ) { a, b -> Cons(f(a), b) }

fun <A, B> List<A>.foldRight(z: B, f: (A, B) -> B): B =
    when (this) {
        is Nil -> z
        is Cons -> f(this.head, this.tail.foldRight(z, f))
    }

fun List<Double>.sum(): Double =
    this.foldRight(0.0, { a, b -> a + b })

fun <A> List<A>.size(): Int =
    this.foldRight(0, { _, acc -> 1 + acc })

fun List<Double>.isEmpty(): Boolean = when (this) {
    is Nil -> true
    is Cons -> false
}

fun <A> catches(a: () -> A): Option<A> =
    try {
        Some(a())
    } catch (e: Throwable) {
        None
    }