package by.varyvoda.cos2.view

import by.varyvoda.cos2.parameters.ParameterSuite
import by.varyvoda.cos2.signal.SignalAnalyzer
import by.varyvoda.cos2.signal.SignalGenerator
import by.varyvoda.cos2.signal.SignalPoint
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Data
import javafx.scene.chart.XYChart.Series
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Priority
import tornadofx.*
import kotlin.math.pow


class LabView : View("COS 2") {

    override fun onDock() {
        super.onDock()
        primaryStage.isMaximized = true
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED) {
            when (it.code) {
                KeyCode.DIGIT1 -> changeVariant(1)
                KeyCode.DIGIT2 -> changeVariant(2)
                KeyCode.DIGIT3 -> changeVariant(3)
                KeyCode.DIGIT4 -> changeVariant(4)
                KeyCode.DIGIT5 -> changeVariant(5)
                KeyCode.DIGIT6 -> changeVariant(6)
                KeyCode.UP -> changeNQualifier(nQualifier + 1)
                KeyCode.DOWN -> changeNQualifier(nQualifier - 1)
                else -> {}
            }
        }
    }

    private lateinit var variant: ParameterSuite

    private lateinit var plain: XYChart<Number, Number>
    private lateinit var phased: XYChart<Number, Number>

    private var nQualifier = 6
    private var n = 64
    private val k get() = n * variant.K

    override val root = vbox {
        paddingAll = 10
        spacing = 10.0

        plain = getChart()
        phased = getChart()

        add(plain)
        add(phased)

        changeVariant(6)
    }

    private fun changeVariant(variant: Int) {
        if (variant < 1 || variant > 6) return
        this.variant = ParameterSuite.of(variant)

        update()
    }

    private fun changeNQualifier(nQualifier: Int) {
        if (nQualifier < 6 || nQualifier > 12) return
        this.nQualifier = nQualifier
        this.n = 2.0.pow(nQualifier).toInt()

        update()
    }

    private fun update() {
        plain.apply {
            title = fullName("Plain", 0.0)
            set(calculate(0.0))
        }
        phased.apply {
            title = fullName("Phased", variant.phi)
            set(calculate(variant.phi))
        }
    }

    private fun fullName(chartName: String, phi: Double): String {
        return "$chartName of variant ${variant.name} with n = $n and phi = $phi"
    }

    private fun calculate(phase: Double): List<Pair<String, List<SignalPoint>>> {
        val analyzer = SignalAnalyzer()

        val expectedAmplitude = 1.0
        val expectedStandardDeviation = 0.707

        val generateSignal = { n: Int, m: Int -> SignalGenerator(n, phase).batch(m).map { it.y } }

        return listOf(
            "Amplitudes by fourier" to analyzer.error(
                generateSignal, n, k,
                { analyzer.amplitudes(it) },
                expectedAmplitude
            ),
            "Standard deviation by expectation" to analyzer.error(
                generateSignal, n, k,
                { analyzer.mathStandardDeviation(it) },
                expectedStandardDeviation
            ),
            "Standard deviation by dispersion" to analyzer.error(
                generateSignal, n, k,
                { analyzer.dispersionStandardDeviation(it) },
                expectedStandardDeviation
            ),
        )
    }

    private fun getChart(): XYChart<Number, Number> {
        return LineChart(NumberAxis(), NumberAxis()).also {
            it.vgrow = Priority.ALWAYS
            it.animated = false
            it.createSymbols = false
            it.axisSortingPolicy = LineChart.SortingPolicy.NONE
            it.xAxis.label = "m"
            it.yAxis.label = "Deviation"
        }
    }
}

private fun XYChart<Number, Number>.set(signals: List<Pair<String, List<SignalPoint>>>) {
    data.setAll(
        signals.map { signal ->
            Series<Number?, Number?>().also { series ->
                series.name = signal.first
                series.data.setAll(signal.second.map { Data(it.x, it.y) })
            }
        }
    )
}