package org.example.kmpproject.ui.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.example.kmpproject.navigation.HomeEvent
import org.example.kmpproject.navigation.IHomeComponent

@Composable
fun SearchBarItem(component: IHomeComponent) {

    val searchText by component.searchText.collectAsState()

    OutlinedTextField(
        value         = searchText,
        onValueChange = { component.onEvent(HomeEvent.UpdateSearchText(it)) },
        leadingIcon   = {
            Icon(
                imageVector        = Icons.Default.Search,
                contentDescription = "Search",
                tint               = Color.Gray
            )
        },
        trailingIcon = {
            // ✅ بيعمل search لما تضغط على الأيقونة
            IconButton(onClick = { component.onEvent(HomeEvent.ClickSearch) }) {
                Icon(
                    imageVector        = Icons.Outlined.Add,
                    contentDescription = "Filter",
                    tint               = Color.Black
                )
            }
        },
        placeholder = {
            Text("Search clothes...", fontFamily = FontFamily.SansSerif, color = Color.Gray)
        },
        singleLine = true,
        colors     = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor   = Color.Black,
            unfocusedBorderColor = Color(200, 200, 200),
            backgroundColor      = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(38.dp)
    )
}