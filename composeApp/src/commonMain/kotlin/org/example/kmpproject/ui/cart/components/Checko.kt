package org.example.kmpproject.ui.cart.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

/* ───────────────────────────────────────────────────────────────────────────── */
/*  PUBLIC COMPOSABLE                                                           */
/* ───────────────────────────────────────────────────────────────────────────── */

@Composable
fun CheckOut2(
    promoCode: String,
    orderAmount: String,
    discount: String,
    total: String,
    onPromoCodeChange: (String) -> Unit,
    onApplyClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier

            .fillMaxWidth()
            .background(
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            )
            .padding(horizontal = 50.dp, vertical = 32.dp)
    ) {

        /* ── Promo‑code field ─────────────────────────────────────────────── */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White)
                .padding(start = 20.dp, end = 4.dp),      // text left, Apply right
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text input (borderless)
            TextField(
                value = promoCode,
                onValueChange = onPromoCodeChange,
                placeholder = { Text("Promo Code") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)            // take remaining width
            )

            // “Apply” chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Black)
                    .clickable { onApplyClick() }
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Apply", color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(32.dp))

        /* ── Price rows ───────────────────────────────────────────────────── */
        PriceRow("Order Amount", orderAmount)
        DashedDivider()
        PriceRow("Discount", discount)
        DashedDivider()
        PriceRow("Total Payment", total, bold = true)

        Spacer(Modifier.height(32.dp))

        /* ── Checkout button ──────────────────────────────────────────────── */
        Button(
            onClick = onCheckoutClick,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Checkout", fontSize = 16.sp, color = Color.White)
        }
    }
}

/* ───────────────────────────────────────────────────────────────────────────── */
/*  INTERNAL HELPER COMPOSABLES                                                 */
/* ───────────────────────────────────────────────────────────────────────────── */

@Composable
private fun PriceRow(label: String, value: String, bold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 17.sp,
            fontWeight = if (bold) androidx.compose.ui.text.font.FontWeight.Bold
            else androidx.compose.ui.text.font.FontWeight.Normal
        )
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = if (bold) androidx.compose.ui.text.font.FontWeight.Bold
            else androidx.compose.ui.text.font.FontWeight.Normal
        )
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
        val gap = 8.dp.toPx()
        var startX = 0f
        while (startX < size.width) {
            drawLine(
                color = Color.Black,
                start = androidx.compose.ui.geometry.Offset(startX, 0f),
                end = androidx.compose.ui.geometry.Offset(
                    x = kotlin.math.min(startX + dashWidth, size.width),
                    y = 0f
                ),
                strokeWidth = 1.dp.toPx()
            )
            startX += dashWidth + gap
        }
    }
}

/* ───────────────────────────────────────────────────────────────────────────── */
/*  ANDROID‑STUDIO PREVIEW (Works only in androidMain)                          */
/* ───────────────────────────────────────────────────────────────────────────── */

@Preview()
@Composable
private fun CheckOutPreview2() {
    var promo by remember { mutableStateOf("") }

    Surface(color = Color(0xFF101010)) {            // dark screen bg
        CheckOut2(
            promoCode = promo,
            orderAmount = "$2445",
            discount = "$2445",
            total = "$2445",
            onPromoCodeChange = { promo = it },
            onApplyClick = {},
            onCheckoutClick = {}
        )
    }
}

