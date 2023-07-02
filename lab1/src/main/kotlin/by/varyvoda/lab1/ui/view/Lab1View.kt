package by.varyvoda.lab1.ui.view

import by.varyvoda.lab1.domain.ParameterSuite
import by.varyvoda.lab1.domain.PolyHarmonic
import by.varyvoda.lab1.domain.SignalGenerator
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Data
import javafx.scene.chart.XYChart.Series
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Priority
import tornadofx.*
import java.util.stream.IntStream
import kotlin.streams.toList

class Lab1View : View("Lab1") {

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

    private lateinit var a: XYChart<Number, Number>
    private lateinit var b: XYChart<Number, Number>
    private lateinit var c: XYChart<Number, Number>
    private lateinit var d: XYChart<Number, Number>
    private lateinit var e: XYChart<Number, Number>

    private var variant = 6
    private var nQualifier = 1
    private val n get() = nQualifier * 512

    override val root = scrollpane {
        vbox {
            a = getChart()
            b = getChart()
            c = getChart()
            d = getChart()
            e = getChart()
            children.addAll(a, b, c, d, e)
            update()
        }
    }

    private fun changeVariant(variant: Int) {
        if (variant < 1 || variant > 6) return
        this.variant = variant
        update()
    }

    private fun changeNQualifier(nQualifier: Int) {
        if (nQualifier < 1 || nQualifier > 5) return
        this.nQualifier = nQualifier
        update()
    }

    private fun update() {
        ParameterSuite.ofA(variant).let { suite ->
            a.title = "${
                title(
                    "A",
                    variant
                )
            } | A=${suite.A}, f=${suite.f}, phis=${suite.phis.mapIndexed { i, v -> c("π/${v / Math.PI}", i) }}}"
            a.data.setAll(
                suite.phis.map { phi ->
                    Series<Number, Number>().also { series ->
                        series.data.setAll(
                            SignalGenerator(suite.A, suite.f, n, phi).all()
                                .map { Data(it.x, it.y) }
                        )
                    }
                }
            )
        }

        ParameterSuite.ofB(variant).let { suite ->
            b.title =
                "${title("B", variant)} | A=${suite.A}, phi=${suite.phi}, fs=${suite.fs.mapIndexed { i, v -> c(v, i) }}"
            b.data.setAll(
                suite.fs.map { f ->
                    Series<Number, Number>().also { series ->
                        series.data.setAll(
                            SignalGenerator(suite.A, f, n, suite.phi).all()
                                .map { Data(it.x, it.y) }
                        )
                    }
                }
            )
        }

        ParameterSuite.ofC(variant).let { suite ->
            c.title =
                "${title("C", variant)} | f=${suite.f}, phi=${suite.phi}, As=${suite.As.mapIndexed { i, v -> c(v, i) }}"
            c.data.setAll(
                suite.As.map { A ->
                    Series<Number, Number>().also { series ->
                        series.data.setAll(
                            SignalGenerator(A, suite.f, n, suite.phi).all()
                                .map { Data(it.x, it.y) }
                        )
                    }
                }
            )
        }

        ParameterSuite.ofD(variant).let { suite ->
            d.title = "D"
            val generators = suite.map { SignalGenerator(it.A, it.f, n, it.phi) }
            d.data.setAll(
                Series<Number, Number>().also { series ->
                    series.data.setAll(
                        IntStream.range(0, n)
                            .mapToObj { nextN ->
                                Data<Number, Number>(nextN, generators.stream().mapToDouble { it.compute(nextN) }.sum())
                            }
                            .toList()
                    )
                }
            )
        }

        ParameterSuite.ofE().let { suite ->
            e.title = "E"
            e.data.setAll(
                Series<Number, Number>().also { series ->
                    series.data.setAll(
                        PolyHarmonic(
                            suite.map { it.A },
                            suite.map { it.deltaA },
                            suite.map { it.f },
                            suite.map { it.deltaF },
                            n,
                            suite.map { it.phi },
                            suite.map { it.deltaPhi },
                        )
                            .all()
                            .map { Data(it.x, it.y) }
                    )
                }
            )
        }
    }

    private fun title(part: String, variant: Int): String {
        return "Variant $variant of part $part"
    }

    private fun getChart(): XYChart<Number, Number> {
        return LineChart(NumberAxis(), NumberAxis()).also {
            it.xAxis.label = "n"
            it.yAxis.label = "x(n)"
            it.vgrow = Priority.ALWAYS
            it.isLegendVisible = false
            it.animated = false
            it.createSymbols = false
            it.minHeight = 400.0
            it.minWidth = 1500.0
        }
    }

    private fun c(value: Any, index: Int): String {
        val colors = listOf("red", "orange", "green", "blue", "purple")
        return "${colors[index]}=$value"
    }
}
