package org.example.kmpproject.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClothLazyRowBox(
    selected: Boolean,
    text    : String,
    onClick : () -> Unit
) {
    // ✅ Animated color transition بدل hardcoded
    val bgColor   by animateColorAsState(
        targetValue   = if (selected) Color.Black else Color.White,
        animationSpec = tween(200), label = "bg"
    )
    val textColor by animateColorAsState(
        targetValue   = if (selected) Color.White else Color.Black,
        animationSpec = tween(200), label = "text"
    )

    Box(
        modifier = Modifier
            .height(34.dp)
            .clip(RoundedCornerShape(38.dp))
            .background(bgColor)
            .clickable { onClick() }
            .then(
                if (!selected) Modifier.border(1.dp, Color.Black, RoundedCornerShape(38.dp))
                else Modifier
            )
            .padding(horizontal = 17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = text,
            fontSize   = 14.sp,
            color      = textColor,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontFamily = FontFamily.SansSerif
        )
    }
}