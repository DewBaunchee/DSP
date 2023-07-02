package by.varyvoda.lab1

import by.varyvoda.lab1.ui.view.Lab1View
import tornadofx.App
import tornadofx.launch

class AdemisDesktopApp : App(Lab1View::class)

fun main(args: Array<String>) {
    launch<AdemisDesktopApp>(args)
}