package by.varyvoda.cos2.parameters

class ParameterSuite(val name: Int, val K: Double, val phi: Double) {

    companion object {

        private val variants = listOf(
            ParameterSuite(1, 3.0 / 4, ofPi(1.0 / 32)),
            ParameterSuite(2, 1.0 / 4, ofPi(1.0 / 16)),
            ParameterSuite(3, 3.0 / 4, ofPi(1.0 / 4)),
            ParameterSuite(4, 1.0 / 4, ofPi(2.0 / 3)),
            ParameterSuite(5, 3.0 / 4, ofPi(1.0 / 8)),
            ParameterSuite(6, 1.0 / 4, ofPi(1.0 / 2)),
        )

        fun of(variant: Int): ParameterSuite {
            return variants[variant - 1]
        }

        private fun ofPi(values: Double): Double {
            return values * Math.PI
        }
    }

}