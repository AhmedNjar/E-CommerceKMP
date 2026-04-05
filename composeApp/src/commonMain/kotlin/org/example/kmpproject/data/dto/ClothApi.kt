package org.example.kmpproject.data.dto

interface ClothApi {
    suspend fun getClothesByTitle(title: String): List<Cloth>
    suspend fun getAvailableClothes(available: Boolean): List<Cloth>
    suspend fun getAllClothes(): List<Cloth>
}