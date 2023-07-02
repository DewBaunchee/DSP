package by.varyvoda.lab1.domain

import kotlin.math.PI

class ParameterSuite {

    companion object {

        private val aSuite = listOf(
            A(10, 2, ofPi(0.0, 1.0 / 6, 1.0 / 4, 1.0 / 2, 1.0)),
            A(5, 1, ofPi(1.0 / 4, 1.0 / 2, 3.0 / 4, 0.0, 1.0)),
            A(8, 4, ofPi(1.0 / 6, 1.0 / 3, 2.0 / 3, 1.0 / 4, 0.0)),
            A(6, 3, ofPi(2.0, 1.0 / 6, 1.0 / 2, 0.0, 3.0 / 4)),
            A(7, 5, ofPi(1.0, 0.0, 1.0 / 3, 1.0 / 6, 1.0 / 2)),
            A(9, 4, ofPi(1.0 / 3, 3.0 / 4, 2.0, 1.0, 1.0 / 6))
        )

        private val bSuite =
            listOf(
                B(3, PI / 2, listOf(5, 4, 2, 6, 3)),
                B(1, PI, listOf(1, 3, 2, 4, 10)),
                B(4, 0.0, listOf(8, 1, 5, 4, 9)),
                B(8, 1.0 / 4, listOf(2, 4, 3, 7, 5)),
                B(5, 3.0 / 4, listOf(1, 5, 11, 6, 3)),
                B(7, 1.0 / 6, listOf(4, 8, 2, 1, 9))
            )

        private val cSuite =
            listOf(
                C(1, 1.0 / 2, listOf(2, 3, 6, 5, 1)),
                C(4, 1.0, listOf(3, 5, 10, 4, 8)),
                C(2, 0.0, listOf(8, 3, 2, 1, 4)),
                C(5, 1.0 / 4, listOf(2, 2, 8, 3, 1)),
                C(3, 3.0 / 4, listOf(1, 2, 11, 4, 2)),
                C(7, 1.0 / 6, listOf(4, 5, 3, 1, 7))
            )

        private val dSuite =
            listOf(
                listOf(
                    D(1, 1, ofPi(0.0)),
                    D(1, 2, ofPi(1 / 4.0)),
                    D(1, 3, ofPi(1 / 6.0)),
                    D(1, 4, ofPi(2.0)),
                    D(1, 4, ofPi(1.0)),
                ),
                listOf(
                    D(1, 1, ofPi(0.0)),
                    D(1, 2, ofPi(1 / 4.0)),
                    D(1, 3, ofPi(1 / 6.0)),
                    D(1, 4, ofPi(2.0)),
                    D(1, 4, ofPi(1.0)),
                ),
                listOf(
                    D(1, 1, ofPi(0.0)),
                    D(1, 2, ofPi(1 / 4.0)),
                    D(1, 3, ofPi(1 / 6.0)),
                    D(1, 4, ofPi(2.0)),
                    D(1, 4, ofPi(1.0)),
                ),
                listOf(
                    D(1, 1, ofPi(0.0)),
                    D(1, 2, ofPi(1 / 4.0)),
                    D(1, 3, ofPi(1 / 6.0)),
                    D(1, 4, ofPi(2.0)),
                    D(1, 4, ofPi(1.0)),
                ),
                listOf(
                    D(1, 1, ofPi(0.0)),
                    D(1, 2, ofPi(1 / 4.0)),
                    D(1, 3, ofPi(1 / 6.0)),
                    D(1, 4, ofPi(2.0)),
                    D(1, 4, ofPi(1.0)),
                ),
                listOf(
                    D(7, 1, ofPi(1.0)),
                    D(7, 2, ofPi(1 / 4.0)),
                    D(7, 3, ofPi(0.0)),
                    D(7, 4, ofPi(3 / 4.0)),
                    D(7, 5, ofPi(1 / 2.0)),
                ),
            )

        fun ofA(variant: Int): A {
            return aSuite[variant - 1]
        }

        fun ofB(variant: Int): B {
            return bSuite[variant - 1]
        }

        fun ofC(variant: Int): C {
            return cSuite[variant - 1]
        }

        fun ofD(variant: Int): List<D> {
            return dSuite[variant - 1]
        }

        fun ofE(): List<E> {
            return listOf(
                E(10, 1, 5, -1, PI, PI / 13),
                E(10, 1, -2, -1, PI / 2.0, PI / 13),
                E(10, -1, 3, -1, 2.0 * PI, PI / 13),
                E(10, 1, 2, -1, PI / 3.0, PI / 13),
                E(10, 1, 4, -1, PI / 4.0, PI / 13),
            )
        }

        private fun ofPi(vararg ratio: Double): List<Double> {
            return ratio.map { it * PI }
        }

        private fun ofPi(ratio: Double): Double {
            return ratio * PI
        }
    }

    data class A(val A: Int, val f: Int, val phis: List<Double>)
    data class B(val A: Int, val phi: Double, val fs: List<Int>)
    data class C(val f: Int, val phi: Double, val As: List<Int>)
    data class D(val A: Int, val f: Int, val phi: Double)
    data class E(val A: Int, val deltaA: Int, val f: Int, val deltaF: Int, val phi: Double, val deltaPhi: Double)
}