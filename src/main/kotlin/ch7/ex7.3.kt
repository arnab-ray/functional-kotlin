package ch7

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.*

data class TimedMap2Future<A, B, C>(
    val pa: Future<A>,
    val pb: Future<B>,
    val f: (A, B) -> C
) : Future<C> {

    override fun isDone(): Boolean = true

    override fun get(): C = f(pa.get(), pb.get())

    override fun get(to: Long, tu: TimeUnit): C {
        val timeoutMillis = TimeUnit.MILLISECONDS.convert(to, tu)

        val start = System.currentTimeMillis()
        val a = pa.get(to, tu)
        val remainder = timeoutMillis - (System.currentTimeMillis() - start)

        val b = pb.get(remainder, TimeUnit.MILLISECONDS)
        return f(a, b)
    }

    override fun cancel(b: Boolean): Boolean = false

    override fun isCancelled(): Boolean = false
}

fun <A, B, C> map2(
    a: Par3<A>,
    b: Par3<B>,
    f: (A, B) -> C
): Par3<C> = {
    es: ExecutorService ->
    val af: Future<A> = a(es)
    val bf: Future<B> = b(es)
    TimedMap2Future(af, bf, f)
}

class Exercise3 : WordSpec({

    val es: ExecutorService =
        ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS, LinkedBlockingQueue()
        )

    "map2" should {
        "allow two futures to run within a given timeout" {

            val pa = Pars.fork {
                Thread.sleep(400L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(500L)
                Pars.unit("1")
            }
            val pc: Par3<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                pc(es).get(1, TimeUnit.SECONDS) shouldBe 2L
            }
        }

        "timeout if first future exceeds timeout" {

            val pa = Pars.fork {
                Thread.sleep(1100L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(500L)
                Pars.unit("1")
            }
            val pc: Par3<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                shouldThrow<TimeoutException> {
                    pc(es).get(1, TimeUnit.SECONDS)
                }
            }
        }

        "timeout if second future exceeds timeout" {

            val pa = Pars.fork {
                Thread.sleep(100L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(1000L)
                Pars.unit("1")
            }
            val pc: Par3<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                shouldThrow<TimeoutException> {
                    pc(es).get(1, TimeUnit.SECONDS)
                }
            }
        }
    }
})