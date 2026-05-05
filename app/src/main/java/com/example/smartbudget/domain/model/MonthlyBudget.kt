package com.example.smartbudget.domain.model

data class MonthlyBudget(
    val id: Long = 0,
    val month: String, // Format: "2026-05"
    val categoryId: Long,
    val limitAmount: Double
)