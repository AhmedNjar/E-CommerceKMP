/*package org.example.kmpproject.data.dto

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.kmpproject.data.HttpRoutes

class ClothServiceImpl {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true // دي مهمه وحلت مشاكل
            })
        }
    }

    object ApiClient {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    interface ClothApi {
        suspend fun findByTitle(title: String): List<Cloth>
        suspend fun findByAvailable(availabe: Boolean): List<Cloth>
        suspend fun getAllClothes(): List<Cloth>
    }

    class ClothApiImpl() : ClothApi {
        override suspend fun findByTitle(title: String): List<Cloth> {
            return ApiClient.client.get(HttpRoutes.CLOTHES).body()
        }

        override suspend fun findByAvailable(availabe: Boolean): List<Cloth> {
            return ApiClient.client.get(HttpRoutes.CLOTHES).body()
        }

        override suspend fun getAllClothes(): List<Cloth> {
            return ApiClient.client.get(HttpRoutes.CLOTHES).body()
        }
    }




}*/
package org.example.kmpproject.data.dto

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.kmpproject.data.HttpRoutes
import org.example.kmpproject.data.network.ApiClient

class ClothServiceImpl : ClothApi {

    private val client = ApiClient.client

    override suspend fun getClothesByTitle(title: String): List<Cloth> {
        return try {
            // بنستقبل الـ Response اللي جواه قائمة الملابس
            val apiResponse: Response = client.get("${HttpRoutes.CLOTHES}/search") {
                parameter("title", title)
            }.body()

            apiResponse.clothes
        } catch (e: Exception) {
            println("Error fetching by title: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getAvailableClothes(available: Boolean): List<Cloth> {
        return try {
            val apiResponse: Response = client.get("${HttpRoutes.CLOTHES}/available") {
                parameter("available", available)
            }.body()

            apiResponse.clothes
        } catch (e: Exception) {
            println("Error fetching available: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getAllClothes(): List<Cloth> {
        return try {
            val apiResponse: Response = client.get(HttpRoutes.CLOTHES).body()
            apiResponse.clothes
        } catch (e: Exception) {
            println("Error fetching all: ${e.message}")
            emptyList()
        }
    }
}