package org.example.kmpproject.data.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val clothes: List<Cloth>
)

@Serializable
data class Cloth(
    val id         : String,
    val title      : String,
    val description: String,
    val color      : Long,
    val image      : String,
    val price      : Int,
    val available  : Boolean,
    val company    : String,
    val evaluation : Int,
    val sharedAt   : Instant,
    val category   : String? = null   // ✅ اتضافت
)