package ch7

import java.util.concurrent.ExecutorService

fun <A> join(a: Par3<Par3<A>>): Par3<A> =
    { es: ExecutorService -> a(es).get().invoke(es) }

fun <A, B> flatMapViaJoin(pa: Par3<A>, f: (A) -> Par3<B>): Par3<B> =
    join(map(pa, f))

fun <A, B> flatMap(pa: Par3<A>, f: (A) -> Par3<B>): Par3<B> =
    { es: ExecutorService ->
        map(pa, f).invoke(es).get().invoke(es)
    }

fun <A> joinViaFlatMap(a: Par3<Par3<A>>): Par3<A> =
    flatMap(a) { it }