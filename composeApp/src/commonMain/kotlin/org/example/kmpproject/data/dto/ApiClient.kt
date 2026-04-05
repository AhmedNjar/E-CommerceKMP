package org.example.kmpproject.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// client واحد بس للتطبيق كله — singleton
object ApiClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint        = true
                ignoreUnknownKeys  = true
            })
        }
    }
}