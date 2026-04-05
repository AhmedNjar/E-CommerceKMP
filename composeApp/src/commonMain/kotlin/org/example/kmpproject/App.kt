package org.example.kmpproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import e_commercekmp.composeapp.generated.resources.Res
import e_commercekmp.composeapp.generated.resources.compose_multiplatform
import org.example.kmpproject.navigation.RootComponent
import org.example.kmpproject.ui.cart.CartPage
import org.example.kmpproject.ui.home.Home
import org.example.kmpproject.ui.main_screen.RootContent
import org.example.kmpproject.ui.product.ProductPage

@Composable
@Preview
fun App(
    root : RootComponent
) {
    MaterialTheme {
        RootContent(root)
    }
}