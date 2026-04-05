package org.example.kmpproject.data.model

// ── Domain model للـ cart ─────────────────────────────────────────────────────
// انتقلت من CartPage.kt (UI layer) لهنا عشان cartRepo و ProductComponent يقدروا يستخدموها
data class CartItemData(
    val id      : String,
    val title   : String,
    val price   : Int,
    val image   : String,
    val quantity: Int = 1
)