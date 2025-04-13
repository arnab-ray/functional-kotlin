package ch7

import arrow.core.extensions.list.traverse.map
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture.completedFuture
import java.util.concurrent.ExecutorService


object Pars2 {

    fun <A, B> asyncF(f: (A) -> B): (A) -> Par3<B> =
        { a -> lazyUnit { f(a) } }

    fun <A> unit(a: A): Par3<A> =
        { es: ExecutorService -> completedFuture(a) }

    fun <A> fork(
        a: () -> Par3<A>
    ): Par3<A> =
        { es: ExecutorService ->
            es.submit(Callable<A> { a()(es).get() })
        }

    fun <A> lazyUnit(a: () -> A): Par3<A> =
        fork { unit(a()) }

    // Ex 7.4
    fun <A, B> map(pa: Par3<A>, f: (A) -> B): Par3<B> =
        map2(pa, unit(Unit), { a, _ -> f(a) })

    val <A> List<A>.head: A
        get() = first()

    val <A> List<A>.tail: A
        get() = last()

    val Nil = listOf<Nothing>()

    fun <A> sequence(ps: List<Par3<A>>): Par3<List<A>> =
        when {
            ps.isEmpty() -> unit(Nil)
            ps.size == 1 -> map(ps.head) { listOf(it) }
            else -> {
                val (l, r) = ps.splitAt(ps.size / 2)
                map2(sequence(l), sequence(r)) { la, lb -> la + lb}
            }
        }

    fun <A, B> parMap(
        ps: List<A>,
        f: (A) -> B
    ): Par3<List<B>> = fork {
        val fbs: List<Par3<B>> = ps.map(asyncF(f))
        sequence(fbs)
    }

    fun <A> parFilter(
        sa: List<A>,
        f: (A) -> Boolean
    ): Par3<List<A>> = fork {
        val fbs: List<Par3<A>> = sa.map{ lazyUnit { it } }
        map(sequence(fbs)) { la: List<A> ->
            la.flatMap {
                if (f(it)) listOf(it) else emptyList()
            }
        }
    }


    fun <A> run(a: Par<A>): A = TODO()
}