package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.StateFlow

interface IProfileComponent {

    // ── State ─────────────────────────────────────────────────────────────
    val username    : StateFlow<String>
    val userId      : StateFlow<String>
    val avatarUrl   : StateFlow<String>   // "" = use local drawable
    val ordersCount : StateFlow<Int>
    val wishlistCount: StateFlow<Int>
    val reviewsCount: StateFlow<Int>

    // ── Actions ───────────────────────────────────────────────────────────
    fun onEditProfile()
    fun onChangeAvatar()
    fun onMyOrders()
    fun onSavedAddresses()
    fun onPaymentMethods()
    fun onNotifications()
    fun onHelpAndSupport()
    fun onLogout()
    fun goBack()
}