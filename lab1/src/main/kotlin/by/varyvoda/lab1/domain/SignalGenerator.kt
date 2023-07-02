package by.varyvoda.lab1.domain

import kotlin.math.PI
import kotlin.math.sin

class SignalGenerator(
    private val A: Int,
    private val f: Int,
    private val N: Int,
    private val phi: Double
) {

    fun compute(n: Int): Double {
        return A.toDouble() * sin(2 * PI * f * n / N + phi)
    }

    fun all(): List<SignalPoint> {
        return List(N) { SignalPoint(it, compute(it)) }
    }
}