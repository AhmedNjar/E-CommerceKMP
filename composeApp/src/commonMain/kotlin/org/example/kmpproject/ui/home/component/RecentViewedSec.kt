package org.example.kmpproject.ui.home.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kmpproject.navigation.IHomeComponent

@Composable
fun RecentViewedSec(component: IHomeComponent) {

    val clothes   by component.cloths.collectAsState()
    val isLoading by component.isLoading.collectAsState()
    val error     by component.error.collectAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // ── Section header ────────────────────────────────────────────────
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text       = "For You",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text     = "See all",
                fontSize = 13.sp,
                color    = Color(77, 160, 255)
            )
        }

        // ── Loading state ─────────────────────────────────────────────────
        if (isLoading) {
            Box(
                modifier          = Modifier.fillMaxWidth().height(190.dp),
                contentAlignment  = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(77, 160, 255))
            }
            return@Column
        }

        // ── Error state ───────────────────────────────────────────────────
        if (error != null) {
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text      = "⚠️ Couldn't load items",
                    color     = Color.Gray,
                    fontSize  = 14.sp
                )
            }
            return@Column
        }

        // ── Empty state ───────────────────────────────────────────────────
        if (clothes.isEmpty()) {
            Box(
                modifier         = Modifier.fillMaxWidth().height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No items found 🙈", color = Color.Gray, fontSize = 14.sp)
            }
            return@Column
        }

        // ── Grid: 2 items per row with fade-in animation ──────────────────
        clothes.chunked(2).forEachIndexed { rowIndex, rowItems ->
            AnimatedVisibility(
                visible = true,
                enter   = fadeIn(tween(300 + rowIndex * 80)) +
                        slideInVertically(initialOffsetY = { it / 3 }, animationSpec = tween(300 + rowIndex * 80))
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    modifier              = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { cloth ->
                        RecentViewedBox(component = component, cloth = cloth)
                    }
                    if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}