package ch7

import java.util.concurrent.ExecutorService

object Ex7_12 {
    fun <A, B> chooser(pa: Par3<A>, choices: (A) -> Par3<B>): Par3<B> =
        { es: ExecutorService ->
            choices(pa(es).get()).invoke(es)
        }

    fun <K, V> choiceMap(
        key: Par3<K>,
        choices: Map<K, Par3<V>>
    ): Par3<V> = {
        es: ExecutorService ->
            chooser(key) { choices.getValue(it) }(es)
    }

    fun <A> choiceN(n: Par3<Int>, choices: List<Par3<A>>): Par3<A> =
        { es: ExecutorService ->
            chooser(n) { choices[it] }(es)
        }

    fun <A> choice(cond: Par3<Boolean>, t: Par3<A>, f: Par3<A>): Par3<A> =
        { es: ExecutorService ->
            chooser(cond) { if (it) t else f }(es)
        }
}