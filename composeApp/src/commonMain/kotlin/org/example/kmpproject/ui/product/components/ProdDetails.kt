package org.example.kmpproject.ui.product.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kmpproject.navigation.IProductComponent

private val SIZES  = listOf("XS", "S", "M", "L", "XL")
private val COLORS = listOf(
    Color(77,  160, 255),
    Color(255, 119, 76),
    Color(76,  175, 80),
    Color(33,  33,  33)
)

@Composable
fun ProdDetails(component: IProductComponent) {

    val selectedSize by component.selectedSize.collectAsState()
    val addedToCart  by component.addedToCart.collectAsState()

    var selectedColorIndex by remember { mutableStateOf(0) }

    // ── Staggered entrance: each block slides up with delay ───────────────
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // ── Add to cart button feedback ───────────────────────────────────────
    val btnScale by animateFloatAsState(
        targetValue   = if (addedToCart) 0.96f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label         = "btnScale"
    )
    val btnBg by animateColorAsState(
        targetValue   = if (addedToCart) Color(76, 175, 80) else Color(77, 160, 255),
        animationSpec = tween(300),
        label         = "btnBg"
    )

    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // ── (1) Title + company tag ───────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 0) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    text       = component.product,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 22.sp,
                    lineHeight = 28.sp,
                    modifier   = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(191, 221, 255))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("New", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(30, 100, 200))
                }
            }
        }

        // ── (2) Rating row ────────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 80) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // animated stars
                repeat(5) { i ->
                    val starScale by animateFloatAsState(
                        targetValue   = if (visible) 1f else 0f,
                        animationSpec = tween(300, delayMillis = 200 + i * 60),
                        label         = "star$i"
                    )
                    Icon(
                        imageVector        = if (i < 4) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint               = Color(255, 193, 7),
                        modifier           = Modifier.size(16.dp).scale(starScale)
                    )
                }
                Text("4.5", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("(128 reviews)", color = Color.Gray, fontSize = 12.sp)
            }
        }

        // ── (3) Price ─────────────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 140) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text       = "$${component.price}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 26.sp,
                    color      = Color(77, 160, 255)
                )
                Text(
                    text           = "$${(component.price.toIntOrNull() ?: 0) + 30}",
                    color          = Color.LightGray,
                    fontSize       = 16.sp,
                    textDecoration = TextDecoration.LineThrough
                )
                // Discount chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(255, 235, 235))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("−17%", color = Color(220, 50, 50), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Divider(color = Color(240, 240, 240))

        // ── (4) Color selector ────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 200) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Color", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    COLORS.forEachIndexed { index, color ->
                        val isSelected  = selectedColorIndex == index
                        val ringScale  by animateFloatAsState(
                            targetValue   = if (isSelected) 1f else 0f,
                            animationSpec = spring(Spring.DampingRatioMediumBouncy),
                            label         = "ring$index"
                        )
                        Box(
                            modifier          = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { selectedColorIndex = index },
                            contentAlignment  = Alignment.Center
                        ) {
                            // selection ring
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .scale(ringScale)
                                    .clip(CircleShape)
                                    .border(2.dp, color.copy(alpha = 0.6f), CircleShape)
                            )
                        }
                    }
                }
            }
        }

        // ── (5) Size selector ─────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 260) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Size", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("Size Guide", color = Color(77, 160, 255), fontSize = 12.sp,
                        modifier = Modifier.clickable { })
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SIZES.forEach { size ->
                        SizeBox(
                            sizeName   = size,
                            isSelected = size == selectedSize,
                            onClick    = { component.selectSize(size) }
                        )
                    }
                }
            }
        }

        // ── (6) Description ───────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 320) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Description", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                ExpandableDescription(
                    "Rick Owens is an iconic American Fashion designer celebrated " +
                            "for his dark, edgy, and bold architectural silhouettes. " +
                            "Crafted from premium materials with meticulous attention to detail."
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        // ── (7) Action buttons ────────────────────────────────────────────
        StaggeredSlide(visible = visible, delayMs = 380) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Buy Now
                Button(
                    onClick  = { component.buyNow() },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor    = Color.White
                    ),
                    elevation = ButtonDefaults.elevation(6.dp)
                ) {
                    Text("Buy Now", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                // Add to Cart
                Button(
                    onClick  = { component.addToCart() },
                    modifier = Modifier.weight(1f).height(52.dp).scale(btnScale),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(
                        backgroundColor = btnBg,
                        contentColor    = Color.White
                    ),
                    elevation = ButtonDefaults.elevation(6.dp)
                ) {
                    AnimatedContent(
                        targetState  = addedToCart,
                        transitionSpec = {
                            (slideInVertically { -it } + fadeIn()) togetherWith
                                    (slideOutVertically { it } + fadeOut())
                        },
                        label = "cartLabel"
                    ) { added ->
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                Icons.Filled.ShoppingCart, null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text       = if (added) "Added ✓" else "Add to Cart",
                                fontWeight = FontWeight.Bold,
                                fontSize   = 14.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ── Staggered slide-up entrance helper ───────────────────────────────────────
@Composable
private fun StaggeredSlide(
    visible  : Boolean,
    delayMs  : Int,
    content  : @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter   = fadeIn(tween(400, delayMs)) +
                slideInVertically(tween(400, delayMs)) { it / 3 }
    ) {
        content()
    }
}

// ── Size box ──────────────────────────────────────────────────────────────────
@Composable
private fun SizeBox(sizeName: String, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor by animateColorAsState(
        targetValue   = if (isSelected) Color.Black else Color(241, 245, 249),
        animationSpec = tween(200), label = "sizeBg"
    )
    val textColor by animateColorAsState(
        targetValue   = if (isSelected) Color.White else Color(50, 50, 50),
        animationSpec = tween(200), label = "sizeText"
    )
    val sizeScale by animateFloatAsState(
        targetValue   = if (isSelected) 1.08f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label         = "sizeScale"
    )
    Box(
        modifier = Modifier
            .size(50.dp, 46.dp)
            .scale(sizeScale)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = sizeName,
            color      = textColor,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
            fontSize   = 13.sp
        )
    }
}

// ── Expandable description ────────────────────────────────────────────────────
@Composable
private fun ExpandableDescription(text: String) {
    var expanded by remember { mutableStateOf(false) }

    AnimatedContent(
        targetState  = expanded,
        transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(200)) },
        label = "desc"
    ) { isExpanded ->
        Text(
            text = buildAnnotatedString {
                val display = if (isExpanded) text else text.take(90) + "..."
                append(display)
                if (!isExpanded) {
                    withStyle(SpanStyle(color = Color(77, 160, 255), fontWeight = FontWeight.Bold)) {
                        append(" Read more")
                    }
                }
            },
            modifier   = Modifier.clickable { expanded = !expanded },
            color      = Color(80, 80, 80),
            fontSize   = 14.sp,
            lineHeight = 22.sp
        )
    }
}