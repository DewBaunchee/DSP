package by.varyvoda.lab1.domain

import java.util.stream.IntStream
import kotlin.math.PI
import kotlin.math.sin

class PolyHarmonic(
    private var As: List<Int>,
    private val deltasA: List<Int>,
    private var fs: List<Int>,
    private val deltasF: List<Int>,
    private val N: Int,
    private var phis: List<Double>,
    private val deltasPhi: List<Double>
) {

    private val maxA = As.map { it * 1.2 }
    private val maxF = fs.map { it * 1.2 }
    private val maxPhi = phis.map { it * 1.2 }

    fun compute(n: Int): Double {
        val result = IntStream.range(0, 5)
            .mapToDouble {
                As[it].toDouble() * sin(2 * PI * fs[it] * n / N + phis[it])
            }
            .sum()
        As = As.mapIndexed { index, value ->
            if (value < maxA[index]) return@mapIndexed value + deltasA[index]
            return@mapIndexed value
        }
        fs = fs.mapIndexed { index, value ->
            if (value < maxF[index]) return@mapIndexed value + deltasF[index]
            return@mapIndexed value
        }
        phis = phis.mapIndexed { index, value ->
            if (value < maxPhi[index]) return@mapIndexed value + deltasPhi[index]
            return@mapIndexed value
        }
        return result
    }

    fun all(): List<SignalPoint> {
        return List(N) { SignalPoint(it, compute(it)) }
    }
}