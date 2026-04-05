package org.example.kmpproject.navigation

// ✅ sealed interface — مش interface عادي
sealed interface MainScreenEvent {
    data object NavigateToFav     : MainScreenEvent
    data object NavigateToCart    : MainScreenEvent
    data object NavigateToHome    : MainScreenEvent
    data object NavigateToProfile : MainScreenEvent
}