package org.example.kmpproject.ui.profile.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import e_commercekmp.composeapp.generated.resources.Res
import e_commercekmp.composeapp.generated.resources.pfp
import org.jetbrains.compose.resources.painterResource

@Composable
fun InfoSec(
    username     : String,
    userId       : String,
    avatarUrl    : String  = "",
    ordersCount  : Int     = 0,
    wishlistCount: Int     = 0,
    reviewsCount : Int     = 0,
    onChangeAvatar: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {

        // ── Avatar + edit badge ───────────────────────────────────────────
        var pressed by remember { mutableStateOf(false) }
        val avatarScale by animateFloatAsState(
            targetValue = if (pressed) 0.95f else 1f,
            animationSpec = tween(150),
            label = "avatarScale"
        )

        Box(
            modifier = Modifier.size(110.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Avatar image
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(avatarScale)
                    .clip(CircleShape)
                    .border(3.dp, Color(191, 221, 255), CircleShape)
                    .clickable {
                        pressed = true
                        onChangeAvatar()
                    }
            ) {
                if (avatarUrl.isNotBlank()) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = username,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(Res.drawable.pfp),
                        contentDescription = username,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Edit badge
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Change photo",
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
            }
        }

        // reset press state
        LaunchedEffect(pressed) {
            if (pressed) {
                kotlinx.coroutines.delay(200)
                pressed = false
            }
        }

        Spacer(Modifier.height(16.dp))

        // ── Name + ID ─────────────────────────────────────────────────────
        Text(
            text = username,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = userId,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        // ── Stats row ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(191, 221, 255)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(label = "Orders",   value = ordersCount)
            StatDivider()
            StatItem(label = "Wishlist", value = wishlistCount)
            StatDivider()
            StatItem(label = "Reviews",  value = reviewsCount)
        }
    }
}

@Composable
private fun StatItem(label: String, value: Int) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$value",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(80, 80, 80)
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .padding(vertical = 8.dp)
            .background(Color(160, 200, 240))
            //.align(Alignment.CenterVertically)
    )
}