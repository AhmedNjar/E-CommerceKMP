package org.example.kmpproject.ui.product.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import org.example.kmpproject.navigation.IProductComponent
import org.example.kmpproject.navigation.cartRepo

@Composable
fun TopBar(
    pageName : String,
    component: IProductComponent
) {
    val cartItems by cartRepo.cartItems.collectAsState()

    // ── Cart icon bounces when new item added ─────────────────────────────
    var prevCount by remember { mutableStateOf(cartItems.size) }
    var bouncing  by remember { mutableStateOf(false) }
    val cartScale by animateFloatAsState(
        targetValue      = if (bouncing) 1.35f else 1f,
        animationSpec    = spring(Spring.DampingRatioLowBouncy),
        finishedListener = { bouncing = false },
        label            = "cartBounce"
    )
    LaunchedEffect(cartItems.size) {
        if (cartItems.size != prevCount) { bouncing = true; prevCount = cartItems.size }
    }

    Row(
        modifier              = Modifier.fillMaxWidth().height(42.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        // ── Back ──────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .border(1.dp, Color(220, 220, 220), CircleShape)
                .background(Color.White)
                .clickable { component.goBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier           = Modifier.size(22.dp)
            )
        }

        // ── Title ─────────────────────────────────────────────────────────
        Text(
            text          = pageName,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = 16.sp,
            letterSpacing = 0.sp,
            fontFamily    = FontFamily.SansSerif,
            maxLines      = 1
        )

        // ── Cart with badge ───────────────────────────────────────────────
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(220, 220, 220), CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart",
                    modifier           = Modifier.size(20.dp).scale(cartScale)
                )
            }
            if (cartItems.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .offset(x = 2.dp, y = (-2).dp)
                        .clip(CircleShape)
                        .background(Color(220, 50, 50)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text     = "${cartItems.size}",
                        color    = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}