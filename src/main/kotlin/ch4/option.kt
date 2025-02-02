package ch4

sealed class Option<out A> {
    companion object {
        fun <A> empty(): Option<A> = None
    }

    fun isEmpty(): Boolean = this
        .map { false }
        .getOrElse { true }
}

data class Some<out A>(val get: A) : Option<A>()

data object None : Option<Nothing>()