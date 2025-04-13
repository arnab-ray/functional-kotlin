package ch7

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
}