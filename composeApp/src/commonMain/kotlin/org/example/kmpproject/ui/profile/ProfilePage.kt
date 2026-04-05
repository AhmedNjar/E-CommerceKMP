package org.example.kmpproject.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.kmpproject.navigation.IProfileComponent
import org.example.kmpproject.ui.profile.components.InfoSec
import org.example.kmpproject.ui.profile.components.ProfileTopBar
import org.jetbrains.compose.ui.tooling.preview.Preview

// ── ProfilePage ───────────────────────────────────────────────────────────────
@Composable
fun ProfilePage(component: IProfileComponent) {

    val username      by component.username.collectAsState()
    val userId        by component.userId.collectAsState()
    val avatarUrl     by component.avatarUrl.collectAsState()
    val ordersCount   by component.ordersCount.collectAsState()
    val wishlistCount by component.wishlistCount.collectAsState()
    val reviewsCount  by component.reviewsCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, top = 39.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        ProfileTopBar(pageName = "Profile", component = component)

        // ── Avatar + stats ────────────────────────────────────────────────
        InfoSec(
            username      = username,
            userId        = userId,
            avatarUrl     = avatarUrl,
            ordersCount   = ordersCount,
            wishlistCount = wishlistCount,
            reviewsCount  = reviewsCount,
            onChangeAvatar = { component.onChangeAvatar() }
        )

        // ── Menu sections ─────────────────────────────────────────────────
        ProfileMenuSection(title = "Shopping") {
            ProfileMenuItem(
                icon  = Icons.Outlined.ShoppingCart,
                label = "My Orders",
                badge = if (ordersCount > 0) "$ordersCount" else null,
                onClick = { component.onMyOrders() }
            )
            ProfileMenuItem(
                icon  = Icons.Outlined.FavoriteBorder,
                label = "Wishlist",
                badge = if (wishlistCount > 0) "$wishlistCount" else null,
                onClick = { component.onMyOrders() }   // TODO: wishlist navigation
            )
            ProfileMenuItem(
                icon    = Icons.Outlined.LocationOn,
                label   = "Saved Addresses",
                onClick = { component.onSavedAddresses() }
            )
            ProfileMenuItem(
                icon    = Icons.Outlined.AccountBox,
                label   = "Payment Methods",
                onClick = { component.onPaymentMethods() }
            )
        }

        ProfileMenuSection(title = "Preferences") {
            ProfileMenuItem(
                icon    = Icons.Outlined.Notifications,
                label   = "Notifications",
                onClick = { component.onNotifications() }
            )
            ProfileMenuItem(
                icon    = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                label   = "Help & Support",
                onClick = { component.onHelpAndSupport() }
            )
        }

        // ── Logout ────────────────────────────────────────────────────────
        var showLogoutDialog by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(255, 230, 230))
                .clickable { showLogoutDialog = true }
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Logout",
                    tint = Color(200, 50, 50),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(14.dp))
                Text(
                    text = "Logout",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(200, 50, 50)
                )
            }
        }

        // ── Logout confirmation dialog ────────────────────────────────────
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Logout", fontWeight = FontWeight.Bold) },
                text  = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    TextButton(onClick = {
                        showLogoutDialog = false
                        component.onLogout()
                    }) {
                        Text("Logout", color = Color(200, 50, 50), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

// ── Reusable menu components ──────────────────────────────────────────────────

@Composable
private fun ProfileMenuSection(
    title  : String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(242, 242, 242)),
            content = content
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon   : ImageVector,
    label  : String,
    badge  : String?  = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon bg
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(191, 221, 255)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(30, 90, 180),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        // Optional badge
        if (badge != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(191, 221, 255))
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(badge, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(30, 90, 180))
            }
            Spacer(Modifier.width(8.dp))
        }

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

private fun fakeProfileComponent(
    username: String = "Ahmed Njar",
    userId  : String = "@ahmed_njar",
    orders  : Int    = 12,
    wishlist: Int    = 5,
    reviews : Int    = 8
): IProfileComponent = object : IProfileComponent {
    override val username     = MutableStateFlow(username)
    override val userId       = MutableStateFlow(userId)
    override val avatarUrl    = MutableStateFlow("")
    override val ordersCount  = MutableStateFlow(orders)
    override val wishlistCount= MutableStateFlow(wishlist)
    override val reviewsCount = MutableStateFlow(reviews)

    override fun onEditProfile()    = Unit
    override fun onChangeAvatar()   = Unit
    override fun onMyOrders()       = Unit
    override fun onSavedAddresses() = Unit
    override fun onPaymentMethods() = Unit
    override fun onNotifications()  = Unit
    override fun onHelpAndSupport() = Unit
    override fun onLogout()         = Unit
    override fun goBack()           = Unit
}

@Preview @Composable
private fun ProfilePagePreview() {
    Surface { ProfilePage(component = fakeProfileComponent()) }
}

@Preview @Composable
private fun ProfilePageNewUserPreview() {
    Surface { ProfilePage(component = fakeProfileComponent(orders = 0, wishlist = 0, reviews = 0)) }
}