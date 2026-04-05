package org.example.kmpproject.navigation

sealed interface HomeEvent {
    data object  ClickCart              : HomeEvent
    data object  ClickSearch            : HomeEvent
    data object  ClickNotifications     : HomeEvent
    data class   ClickProduct(val productId: String, val product: String, val price: String, val image: String) : HomeEvent
    data class   UpdateSearchText(val text: String) : HomeEvent
    data class   ClickCategory(val category: String) : HomeEvent
}