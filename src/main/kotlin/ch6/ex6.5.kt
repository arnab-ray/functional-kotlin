package ch6

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class Exercise5 : WordSpec({

    fun doubleR(): Rand<Double> =
        map(::double) { it }

    "doubleR" should {

        val unusedRng = object : RNG {
            override fun nextInt(): Pair<Int, RNG> = TODO()
        }

        """generate a max value approaching 1 based on
            Int.MAX_VALUE using Rand""" {

            val rngMax = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    Int.MAX_VALUE to unusedRng
            }

            val doubleRand = doubleR()
            doubleRand(rngMax) shouldBe (0.9999999995343387 to unusedRng)
        }

        "generate a min value of 0 based on 0 using Rand" {

            val rngMin = object : RNG {
                override fun nextInt(): Pair<Int, RNG> =
                    0 to unusedRng
            }

            val doubleRand = doubleR()
            doubleRand(rngMin) shouldBe (0.0 to unusedRng)
        }
    }
})