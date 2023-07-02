package by.varyvoda.cos2

import by.varyvoda.cos2.view.LabView
import javafx.application.Application
import tornadofx.App

class Cos2 : App(LabView::class)

fun main(args: Array<String>) {
    Application.launch(Cos2::class.java, *args)
}