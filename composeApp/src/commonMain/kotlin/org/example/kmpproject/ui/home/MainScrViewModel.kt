package org.example.kmpproject.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.kmpproject.data.dto.Cloth
import org.example.kmpproject.data.dto.ClothServiceImpl

class MainScrViewModel: ViewModel() {

    private val _boxColor = MutableStateFlow(Color.Transparent) // Initial color
    val boxColor = _boxColor.asStateFlow()

    private val _iconColor = MutableStateFlow(Color.Black) // Initial color
    val iconColor = _iconColor.asStateFlow()

    fun changeColor() {
        _boxColor.value = if (_boxColor.value == Color.Transparent) Color.Black else Color.Transparent
        _iconColor.value = if (_iconColor.value == Color.Black) Color.White else Color.Black
    }

    fun firstColor() {
        _boxColor.value = if (_boxColor.value == Color.Transparent) Color.Black else Color.Transparent
        _iconColor.value = if (_iconColor.value == Color.Black) Color.White else Color.Black
    }




}