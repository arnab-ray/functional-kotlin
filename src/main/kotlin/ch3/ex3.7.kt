package ch3

fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> f(xs.head, foldRight(xs.tail, z, f))
    }

fun main() {
    val result = foldRight(
        Cons(1, Cons(2, Cons(3, Nil))),
        List.empty<Int>()
    ) { x, y -> Cons(x, y) }

    println(result)
}