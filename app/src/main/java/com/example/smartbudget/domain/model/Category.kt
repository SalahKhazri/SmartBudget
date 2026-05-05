package com.example.smartbudget.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val icon: String = "💰",
    val color: String = "#FF6B6B",
    val isActive: Boolean = true
) {
    companion object {
        val DEFAULT_CATEGORIES = listOf(
            Category(1, "Alimentation", "🍽️", "#FF6B6B"),
            Category(2, "Transport", "🚌", "#4ECDC4"),
            Category(3, "Logement", "🏠", "#45B7D1"),
            Category(4, "Santé", "💊", "#96CEB4"),
            Category(5, "Loisirs", "🎮", "#FFEAA7"),
            Category(6, "Études", "📚", "#DDA0DD"),
            Category(7, "Shopping", "🛍️", "#98D8C8"),
            Category(8, "Autre", "📦", "#B2BEC3")
        )
    }
}