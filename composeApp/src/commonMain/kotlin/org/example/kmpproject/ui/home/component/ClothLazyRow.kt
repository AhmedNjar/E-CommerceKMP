package org.example.kmpproject.ui.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.kmpproject.navigation.HomeEvent
import org.example.kmpproject.navigation.IHomeComponent

private val CATEGORIES = listOf("All", "Menswear", "Womenswear", "Everywear")

@Composable
fun ClothLazyRow(component: IHomeComponent) {

    val selected by component.selectedCategory.collectAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier              = Modifier.fillMaxWidth().height(34.dp)
    ) {
        items(CATEGORIES) { category ->
            ClothLazyRowBox(
                selected = selected == category,
                text     = category,
                onClick  = { component.onEvent(HomeEvent.ClickCategory(category)) }
            )
        }
    }
}