package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.StateFlow
import org.example.kmpproject.data.dto.Cloth

interface IHomeComponent {
    val cloths           : StateFlow<List<Cloth>>
    val searchText       : StateFlow<String>
    val isLoading        : StateFlow<Boolean>
    val error            : StateFlow<String?>
    val selectedCategory : StateFlow<String>
    val username         : StateFlow<String>

    fun onEvent(event: HomeEvent)
}