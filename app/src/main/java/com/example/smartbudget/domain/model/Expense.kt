package com.example.smartbudget.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val currency: String = "MAD",
    val date: LocalDate,
    val categoryId: Long,
    val note: String = "",
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class PaymentMethod {
    CASH, CARD, TRANSFER
}