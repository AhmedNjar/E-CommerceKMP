package org.example.kmpproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.kmpproject.navigation.RootComponent

fun main(
    root : RootComponent
) = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "E-Commerce KMP",

    ) {
        App(root)
    }
}