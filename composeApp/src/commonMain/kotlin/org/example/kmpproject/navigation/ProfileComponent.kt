package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileComponent(
    componentContext: ComponentContext,
    private val onGoBack          : () -> Unit,
    private val onNavigateOrders  : () -> Unit = {},
    private val onNavigateCheckout: () -> Unit = {}
) : ComponentContext by componentContext, IProfileComponent {

    // ── State ─────────────────────────────────────────────────────────────
    // TODO: اربطهم بـ userRepo أو datastore لما تجيب الداتا من الـ API
    private val _username     = MutableStateFlow("Ahmed Njar")
    private val _userId       = MutableStateFlow("@ahmed_njar")
    private val _avatarUrl    = MutableStateFlow("")         // "" = local drawable
    private val _ordersCount  = MutableStateFlow(12)
    private val _wishlistCount= MutableStateFlow(5)
    private val _reviewsCount = MutableStateFlow(8)

    override val username     : StateFlow<String>  = _username.asStateFlow()
    override val userId       : StateFlow<String>  = _userId.asStateFlow()
    override val avatarUrl    : StateFlow<String>  = _avatarUrl.asStateFlow()
    override val ordersCount  : StateFlow<Int>     = _ordersCount.asStateFlow()
    override val wishlistCount: StateFlow<Int>     = _wishlistCount.asStateFlow()
    override val reviewsCount : StateFlow<Int>     = _reviewsCount.asStateFlow()

    // ── Actions ───────────────────────────────────────────────────────────
    override fun onEditProfile()      { /* TODO: navigate to EditProfile screen */ }
    override fun onChangeAvatar()     { /* TODO: open image picker */ }
    override fun onMyOrders()         { onNavigateOrders() }
    override fun onSavedAddresses()   { /* TODO: navigate to Addresses screen */ }
    override fun onPaymentMethods()   { /* TODO: navigate to Payment screen */ }
    override fun onNotifications()    { /* TODO: navigate to Notifications screen */ }
    override fun onHelpAndSupport()   { /* TODO: open help screen or URL */ }
    override fun onLogout() {
        // TODO: clear session + navigate to Login
    }
    override fun goBack() { onGoBack() }
}