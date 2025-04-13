package ch7

import java.util.concurrent.ExecutorService

fun <A, B> map(pa: Par3<A>, f: (A) -> B): Par3<B> =
    map2(pa, Pars2.unit(Unit), { a, _ -> f(a) })

fun <A> choiceN(n: Par3<Int>, choices: List<Par3<A>>): Par3<A> =
    { es: ExecutorService ->
        choices[n(es).get()].invoke(es)
    }

fun <A> choice(cond: Par3<Boolean>, t: Par3<A>, f: Par3<A>): Par3<A> =
    { es: ExecutorService ->
        choiceN(
            map(cond) { if (it) 1 else 0 },
            listOf(t, f)
        )(es)
    }



