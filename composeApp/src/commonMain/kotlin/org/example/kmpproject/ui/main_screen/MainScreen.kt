package org.example.kmpproject.ui.main_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.kmpproject.navigation.RootComponent
import org.example.kmpproject.navigation.cartRepo
import org.example.kmpproject.ui.cart.CartPage
import org.example.kmpproject.ui.home.Home
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.kmpproject.ui.product.ProductPage
import org.example.kmpproject.ui.profile.ProfilePage

// ── Tab definition ────────────────────────────────────────────────────────────
private data class BottomTab(
    val config       : RootComponent.Configuration,
    val selectedIcon : ImageVector,
    val defaultIcon  : ImageVector,
    val label        : String
)

private val TABS = listOf(
    BottomTab(RootComponent.Configuration.Home,    Icons.Filled.Home,         Icons.Outlined.Home,          "Home"),
    BottomTab(RootComponent.Configuration.Cart,    Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart,  "Cart"),
    BottomTab(RootComponent.Configuration.Fav,     Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder,"Fav"),
    BottomTab(RootComponent.Configuration.Profile, Icons.Filled.Person,       Icons.Outlined.AccountCircle, "Profile"),
)

// ── RootContent ───────────────────────────────────────────────────────────────
@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeConfig = childStack.active.configuration

    // Show bottom bar only on main tabs
    val showBottomBar = activeConfig is RootComponent.Configuration.Home ||
            activeConfig is RootComponent.Configuration.Cart ||
            activeConfig is RootComponent.Configuration.Fav  ||
            activeConfig is RootComponent.Configuration.Profile

    Scaffold(
        modifier  = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                AnimatedBottomBar(
                    activeConfig = activeConfig,
                    onTabSelected = { component.onTabSelected(it) }
                )
            }
        }
    ) { paddingValues ->
        Children(
            stack     = childStack,
            modifier  = Modifier.padding(paddingValues),
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Home    -> Home(instance.component)
                is RootComponent.Child.Cart    -> CartPage(instance.component)
                is RootComponent.Child.Product -> ProductPage(instance.component)
                is RootComponent.Child.Fav     -> FavPage(instance.component)
                is RootComponent.Child.Profile -> ProfilePage(instance.component)
            }
        }
    }
}

// ── Animated bottom bar ───────────────────────────────────────────────────────
@Composable
private fun AnimatedBottomBar(
    activeConfig : RootComponent.Configuration,
    onTabSelected: (RootComponent.Configuration) -> Unit
) {
    val cartItems by cartRepo.cartItems.collectAsState()

    Box(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(horizontal = 56.dp, vertical = 12.dp),
        contentAlignment  = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(Color(228, 228, 228))
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                TABS.forEach { tab ->
                    val isSelected = activeConfig::class == tab.config::class

                    // ── Icon scale bounce on selection ────────────────────
                    val iconScale by animateFloatAsState(
                        targetValue   = if (isSelected) 1.15f else 1f,
                        animationSpec = spring(Spring.DampingRatioMediumBouncy),
                        label         = "scale_${tab.label}"
                    )
                    // ── Background color ──────────────────────────────────
                    val bgColor by animateColorAsState(
                        targetValue   = if (isSelected) Color.Black else Color.Transparent,
                        animationSpec = tween(250),
                        label         = "bg_${tab.label}"
                    )
                    // ── Icon tint ─────────────────────────────────────────
                    val iconTint by animateColorAsState(
                        targetValue   = if (isSelected) Color.White else Color(80, 80, 80),
                        animationSpec = tween(250),
                        label         = "tint_${tab.label}"
                    )

                    Box(contentAlignment = Alignment.TopEnd) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(bgColor)
                                .scale(iconScale)
                                .then(
                                    Modifier.noRippleClickable { onTabSelected(tab.config) }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector        = if (isSelected) tab.selectedIcon else tab.defaultIcon,
                                contentDescription = tab.label,
                                tint               = iconTint,
                                modifier           = Modifier.size(22.dp)
                            )
                        }

                        // ── Cart badge ────────────────────────────────────
                        if (tab.config is RootComponent.Configuration.Cart && cartItems.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .offset(x = 2.dp, y = (-2).dp)
                                    .clip(CircleShape)
                                    .background(Color(220, 50, 50)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text     = "${cartItems.size}",
                                    color    = Color.White,
                                    fontSize = androidx.compose.ui.unit.TextUnit(
                                        8f, androidx.compose.ui.unit.TextUnitType.Sp
                                    ),
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── No ripple clickable helper ────────────────────────────────────────────────
private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this.then(
        Modifier.clickable(
            interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
            indication        = null,
            onClick           = onClick
        )
    )

// ── Previews ──────────────────────────────────────────────────────────────────

// Preview 1-4: Bottom bar على كل tab
@Preview @Composable
private fun BottomBarHomePreview() {
    Surface {
        AnimatedBottomBar(
            activeConfig  = RootComponent.Configuration.Home,
            onTabSelected = {}
        )
    }
}

@Preview @Composable
private fun BottomBarCartPreview() {
    Surface {
        AnimatedBottomBar(
            activeConfig  = RootComponent.Configuration.Cart,
            onTabSelected = {}
        )
    }
}

@Preview @Composable
private fun BottomBarFavPreview() {
    Surface {
        AnimatedBottomBar(
            activeConfig  = RootComponent.Configuration.Fav,
            onTabSelected = {}
        )
    }
}

@Preview @Composable
private fun BottomBarProfilePreview() {
    Surface {
        AnimatedBottomBar(
            activeConfig  = RootComponent.Configuration.Profile,
            onTabSelected = {}
        )
    }
}

// Preview 5: الـ scaffold كاملة مع content placeholder
@Preview @Composable
private fun MainScreenScaffoldPreview() {
    var selected by remember {
        mutableStateOf<RootComponent.Configuration>(RootComponent.Configuration.Home)
    }
    Scaffold(
        modifier  = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedBottomBar(
                activeConfig  = selected,
                onTabSelected = { selected = it }
            )
        }
    ) { padding ->
        Box(
            modifier         = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(245, 247, 250)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material.Text(
                text     = when (selected) {
                    is RootComponent.Configuration.Home    -> "🏠  Home"
                    is RootComponent.Configuration.Cart    -> "🛒  Cart"
                    is RootComponent.Configuration.Fav     -> "❤️  Wishlist"
                    is RootComponent.Configuration.Profile -> "👤  Profile"
                    else                                   -> ""
                },
                fontSize   = androidx.compose.ui.unit.TextUnit(24f, androidx.compose.ui.unit.TextUnitType.Sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                color      = androidx.compose.ui.graphics.Color(80, 80, 80)
            )
        }
    }
}