package org.example.kmpproject.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Instant
import org.example.kmpproject.data.dto.Cloth
import org.example.kmpproject.navigation.HomeEvent
import org.example.kmpproject.navigation.IHomeComponent
import org.example.kmpproject.ui.home.component.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Home(component: IHomeComponent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 37.dp),
        verticalArrangement   = Arrangement.spacedBy(22.dp),
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        TopAppBar(component)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(22.dp)) {
            item { SearchBarItem(component) }
            item { ProductSec(onShopNow = { component.onEvent(HomeEvent.ClickCart) }) }
            item { ClothLazyRow(component) }
            item { RecentViewedSec(component) }
        }
    }
}

// ── Preview helpers ───────────────────────────────────────────────────────────

private val epoch = Instant.fromEpochMilliseconds(0)

private val sampleCloths = listOf(
    Cloth("1", "Nike Air Max 270",   "Great shoe", 0xFF4DA0FF, "https://picsum.photos/seed/shoe1/300/300", 120, true, "Nike",   5, epoch, "Menswear"),
    Cloth("2", "Adidas Ultra Boost", "Comfy",       0xFFFF7700, "https://picsum.photos/seed/shoe2/300/300",  95, true, "Adidas", 4, epoch, "Womenswear"),
    Cloth("3", "Puma RS-X 3.0",      "Stylish",     0xFF4CAF50, "https://picsum.photos/seed/shoe3/300/300",  75, true, "Puma",   4, epoch, "Menswear"),
    Cloth("4", "New Balance 574",    "Classic",     0xFF9C27B0, "https://picsum.photos/seed/shoe4/300/300",  85, true, "NB",     5, epoch, "Everywear"),
)

private fun fakeHomeComponent(
    cloths          : List<Cloth>  = sampleCloths,
    isLoading       : Boolean      = false,
    error           : String?      = null,
    searchText      : String       = "",
    selectedCategory: String       = "All",
    username        : String       = "Ahmed"
): IHomeComponent = object : IHomeComponent {
    override val cloths            = MutableStateFlow(cloths)
    override val searchText        = MutableStateFlow(searchText)
    override val isLoading         = MutableStateFlow(isLoading)
    override val error             = MutableStateFlow(error)
    override val selectedCategory  = MutableStateFlow(selectedCategory)
    override val username          = MutableStateFlow(username)
    override fun onEvent(event: HomeEvent) = Unit
}

// Preview 1 — normal with data
@Preview @Composable
private fun HomePreview() {
    Surface { Home(component = fakeHomeComponent()) }
}

// Preview 2 — loading state
@Preview @Composable
private fun HomeLoadingPreview() {
    Surface { Home(component = fakeHomeComponent(cloths = emptyList(), isLoading = true)) }
}

// Preview 3 — error state
@Preview @Composable
private fun HomeErrorPreview() {
    Surface { Home(component = fakeHomeComponent(cloths = emptyList(), error = "Connection failed")) }
}

// Preview 4 — empty results (after search)
@Preview @Composable
private fun HomeEmptyPreview() {
    Surface { Home(component = fakeHomeComponent(cloths = emptyList(), searchText = "xyz")) }
}

// Preview 5 — category selected
@Preview @Composable
private fun HomeCategoryPreview() {
    Surface { Home(component = fakeHomeComponent(selectedCategory = "Menswear")) }
}