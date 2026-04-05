package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.kmpproject.data.dto.Cloth
import org.example.kmpproject.data.dto.ClothApi
import org.example.kmpproject.data.dto.ClothServiceImpl

class HomeComponent(
    componentContext               : ComponentContext,
    private val onNavigateToProduct: (String, String, String, String) -> Unit, // productId, product, price, image
    private val onNavigateToCart   : () -> Unit
) : ComponentContext by componentContext, IHomeComponent {

    private val scope    = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val clothApi : ClothApi = ClothServiceImpl()

    private val _allCloths        = MutableStateFlow<List<Cloth>>(emptyList())
    private val _searchText       = MutableStateFlow("")
    private val _isLoading        = MutableStateFlow(false)
    private val _error            = MutableStateFlow<String?>(null)
    private val _selectedCategory = MutableStateFlow("All")
    private val _username         = MutableStateFlow("Ahmed")   // TODO: من userRepo

    override val searchText       : StateFlow<String>  = _searchText.asStateFlow()
    override val isLoading        : StateFlow<Boolean> = _isLoading.asStateFlow()
    override val error            : StateFlow<String?> = _error.asStateFlow()
    override val selectedCategory : StateFlow<String>  = _selectedCategory.asStateFlow()
    override val username         : StateFlow<String>  = _username.asStateFlow()

    // ── Filtered list بيتحسب أوتوماتيك لما الـ category تتغير ────────────
    override val cloths: StateFlow<List<Cloth>> =
        combine(_allCloths, _selectedCategory) { list, cat ->
            if (cat == "All") list
            else list.filter { it.category?.equals(cat, ignoreCase = true) == true }
        }.stateIn(scope, SharingStarted.Eagerly, emptyList())

    init { loadClothes() }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickCart          -> onNavigateToCart()
            is HomeEvent.ClickProduct       -> onNavigateToProduct(event.productId, event.product, event.price, event.image)
            is HomeEvent.ClickNotifications -> { /* TODO: navigate to Notifications */ }
            is HomeEvent.UpdateSearchText   -> {
                _searchText.value = event.text
                if (event.text.isBlank()) loadClothes()
            }
            is HomeEvent.ClickSearch        -> searchByTitle(_searchText.value)
            is HomeEvent.ClickCategory      -> _selectedCategory.value = event.category
        }
    }

    private fun loadClothes() {
        scope.launch {
            _isLoading.value = true
            _error.value     = null
            try { _allCloths.value = clothApi.getAvailableClothes(false) }
            catch (e: Exception) { _error.value = e.message }
            finally { _isLoading.value = false }
        }
    }

    private fun searchByTitle(title: String) {
        if (title.isBlank()) return
        scope.launch {
            _isLoading.value = true
            _error.value     = null
            try { _allCloths.value = clothApi.getClothesByTitle(title) }
            catch (e: Exception) { _error.value = e.message }
            finally { _isLoading.value = false }
        }
    }
}