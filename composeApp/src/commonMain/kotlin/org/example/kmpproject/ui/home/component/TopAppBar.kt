package org.example.kmpproject.ui.home.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e_commercekmp.composeapp.generated.resources.Res
import e_commercekmp.composeapp.generated.resources.pfp
import org.example.kmpproject.navigation.IHomeComponent
import org.example.kmpproject.navigation.HomeEvent
import org.example.kmpproject.navigation.cartRepo
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopAppBar(component: IHomeComponent) {

    val username     by component.username.collectAsState()
    val cartCount    by cartRepo.cartItems.collectAsState()

    // ── Cart icon bounce animation when count changes ─────────────────────
    var prevCount by remember { mutableStateOf(cartCount.size) }
    var bouncing  by remember { mutableStateOf(false) }
    val cartScale  by animateFloatAsState(
        targetValue    = if (bouncing) 1.3f else 1f,
        animationSpec  = spring(Spring.DampingRatioMediumBouncy),
        finishedListener = { bouncing = false },
        label          = "cartBounce"
    )
    LaunchedEffect(cartCount.size) {
        if (cartCount.size != prevCount) { bouncing = true; prevCount = cartCount.size }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
        modifier              = Modifier.fillMaxWidth().height(42.dp)
    ) {

        // ── Avatar + greeting ─────────────────────────────────────────────
        Row(
            verticalAlignment   = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter           = painterResource(Res.drawable.pfp),
                contentDescription = username,
                modifier          = Modifier.size(38.dp).clip(CircleShape)
            )
            Column {
                Text(
                    text       = "Hello $username 👋",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text       = "Welcome to our store",
                    fontSize   = 12.sp,
                    color      = Color.Gray,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }

        // ── Action icons ──────────────────────────────────────────────────
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Notifications
            IconCircleButton(onClick = { component.onEvent(HomeEvent.ClickNotifications) }) {
                Icon(
                    imageVector       = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    modifier          = Modifier.size(18.dp)
                )
            }

            // Cart with badge + bounce
            IconCircleButton(onClick = { component.onEvent(HomeEvent.ClickCart) }) {
                BadgedBox(
                    badge = {
                        if (cartCount.isNotEmpty()) {
                            Badge(backgroundColor = Color(220, 50, 50)) {
                                Text(
                                    "${cartCount.size}",
                                    color    = Color.White,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.ShoppingCart,    // ✅ كان Notifications
                        contentDescription = "Cart",
                        modifier           = Modifier.size(18.dp).scale(cartScale)
                    )
                }
            }
        }
    }
}

// ── Reusable circular icon button ─────────────────────────────────────────────
@Composable
private fun IconCircleButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .border(1.dp, Color.Black, CircleShape)
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
        content = { content() }
    )
}