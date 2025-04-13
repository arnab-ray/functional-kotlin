package ch7

import java.util.concurrent.ExecutorService

fun <K, V> choiceMap(
    key: Par3<K>,
    choices: Map<K, Par3<V>>
): Par3<V> = { es: ExecutorService ->
    choices[key(es).get()]!!.invoke(es)
}