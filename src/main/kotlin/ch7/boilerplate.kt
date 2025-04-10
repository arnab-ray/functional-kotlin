package ch7

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

typealias Par3<A> = (ExecutorService) -> Future<A>

object Pars {
    fun <A> unit(a: A): Par3<A> =
        { es: ExecutorService -> UnitFuture(a) } // <1>

    data class UnitFuture<A>(val a: A) : Future<A> {

        override fun get(): A = a

        override fun get(timeout: Long, timeUnit: TimeUnit): A = a

        override fun cancel(evenIfRunning: Boolean): Boolean = false

        override fun isDone(): Boolean = true

        override fun isCancelled(): Boolean = false
    }

    fun <A, B, C> map2(
        a: Par3<A>,
        b: Par3<B>,
        f: (A, B) -> C
    ): Par3<C> = // <2>
        { es: ExecutorService ->
            val af: Future<A> = a(es)
            val bf: Future<B> = b(es)
            UnitFuture(f(af.get(), bf.get())) // <3>
        }

    fun <A> fork(
        a: () -> Par3<A>
    ): Par3<A> = // <4>
        { es: ExecutorService ->
            es.submit(Callable<A> { a()(es).get() })
        }
}