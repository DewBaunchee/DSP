package by.varyvoda.cos2.signal

import kotlin.math.PI
import kotlin.math.sin

class SignalGenerator(private val N: Int, val phi: Double) {

    private fun compute(n: Int): Double {
        return sin(2 * PI * n / N + phi)
    }

    fun batch(m: Int): List<SignalPoint> {
        return List(m) { SignalPoint(it.toDouble(), compute(it)) }
    }
}