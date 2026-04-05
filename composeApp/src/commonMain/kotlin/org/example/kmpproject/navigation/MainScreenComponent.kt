package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext

class MainScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToFav: () -> Unit,
    private val onNavigateToCart: () -> Unit,
    private val onNavigateToHome: () -> Unit,
    private val onNavigateToProfile: () -> Unit
): ComponentContext by componentContext {

    fun onEvent(event: MainScreenEvent){
        when(event){
            MainScreenEvent.NavigateToFav -> onNavigateToFav()
            MainScreenEvent.NavigateToCart -> onNavigateToCart()
            MainScreenEvent.NavigateToHome -> onNavigateToHome()
            MainScreenEvent.NavigateToProfile -> onNavigateToProfile()
        }
    }
}