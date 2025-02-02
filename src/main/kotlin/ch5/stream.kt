package ch5

import ch4.None
import ch4.Option
import ch4.Some

sealed class Stream<out A> {

    companion object {

        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy(hd)
            val tail: Stream<A> by lazy(tl)
            return Cons({ head }, { tail })
        }

        fun <A> of(vararg xs: A): Stream<A> =
            if (xs.isEmpty()) empty()
            else cons({ xs[0] },
                { of(*xs.sliceArray(1 until xs.size)) })

        fun <A> empty(): Stream<A> = Empty
    }

    fun <B> foldRight(z: () -> B, f: (A, () -> B) -> B): B =
        when (this) {
            is Cons -> f(this.head()) { this.tail().foldRight(z, f) }
            is Empty -> z()
        }
}

data class Cons<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>
) : Stream<A>()

data object Empty : Stream<Nothing>()

fun <A> Stream<A>.headOption(): Option<A> =
    when (this) {
        is Empty -> None
        is Cons -> Some(head())
    }