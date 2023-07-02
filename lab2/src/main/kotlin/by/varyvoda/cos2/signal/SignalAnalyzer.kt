package by.varyvoda.cos2.signal

import kotlin.math.*

class SignalAnalyzer {

    fun amplitudes(values: List<Double>): Double {
        val n = values.size
        val componentOf: ((Double) -> Double) -> Double = { func ->
            ((1.0 / n) * values
                    .mapIndexed { i, y -> (y * func(2 * PI * i / n)) }
                    .sum()
                ).pow(2)
        }
        return sqrt(componentOf { cos(it) } + componentOf { sin(it) })
    }

    fun mathStandardDeviation(values: List<Double>): Double {
        val n = values.size
        return sqrt((1.0 / (n + 1)) * values.sumOf { it.pow(2) })
    }

    fun dispersionStandardDeviation(values: List<Double>): Double {
        val n = values.size
        return sqrt(
            (1.0 / (n + 1)) * values.sumOf { it.pow(2) } -
                ((1.0 / (n + 1)) * values.sum()).pow(2)
        )
    }

    fun error(
        signal: (n: Int, m: Int) -> List<Double>,
        n: Int,
        k: Double,
        actualCalculator: (values: List<Double>) -> Double,
        expected: Double
    ): List<SignalPoint> {
        return (floor(k).toInt()..(2 * n)).map { m ->
            SignalPoint(
                m.toDouble(),
                expected - actualCalculator(signal(n, m))
            )
        }
    }
}