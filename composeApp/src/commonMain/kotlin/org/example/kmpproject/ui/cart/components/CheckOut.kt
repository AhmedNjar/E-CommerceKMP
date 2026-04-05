package org.example.kmpproject.ui.cart.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckOut(
    promoCode         : String  = "",
    promoApplied      : Boolean = false,
    orderAmount       : String  = "$0",
    discount          : String  = "$0",
    total             : String  = "$0",
    onPromoCodeChange : (String) -> Unit = {},
    onApplyClick      : () -> Unit = {},
    onCheckoutClick   : () -> Unit = {}
) {
    // promo state now comes from component — no local remember needed

    // ── Apply button color animation ──────────────────────────────────────
    val applyBg by animateColorAsState(
        targetValue = if (promoApplied) Color(0xFF4CAF50) else Color.Black,
        label = "applyBg"
    )

    // ── Card entrance shadow animation ───────────────────────────────────
    val cardElevation by animateDpAsState(
        targetValue = 8.dp,
        label = "cardElevation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(cardElevation, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color(0xFFF2F2F2))
            .padding(horizontal = 32.dp, vertical = 28.dp)
    ) {

        // ── Promo code row ────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White)
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = promoCode,
                onValueChange = onPromoCodeChange,
                placeholder = { Text("Promo Code", color = Color(180, 180, 180)) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(applyBg)
                    .clickable {
                        if (promoCode.isNotBlank()) onApplyClick()
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (promoApplied) "✓ Applied" else "Apply",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Price rows ────────────────────────────────────────────────────
        PriceRow(label = "Order Amount", value = orderAmount)
        DashedDivider()
        PriceRow(label = "Discount", value = discount)
        DashedDivider()
        PriceRow(label = "Total Payment", value = total, bold = true)

        Spacer(Modifier.height(24.dp))

        // ── Checkout button ───────────────────────────────────────────────
        Button(
            onClick = onCheckoutClick,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "Checkout",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun PriceRow(label: String, value: String, bold: Boolean = false) {
    val weight = if (bold) FontWeight.Bold else FontWeight.Normal
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 18.sp, fontWeight = weight)
        Text(text = value, fontSize = 18.sp, fontWeight = weight)
    }
}

@Composable
private fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val dashWidth = 8.dp.toPx()
        val gap = 6.dp.toPx()
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = Color(180, 180, 180),
                start = androidx.compose.ui.geometry.Offset(x, 0f),
                end = androidx.compose.ui.geometry.Offset(
                    kotlin.math.min(x + dashWidth, size.width), 0f
                ),
                strokeWidth = 1.5.dp.toPx()
            )
            x += dashWidth + gap
        }
    }
}