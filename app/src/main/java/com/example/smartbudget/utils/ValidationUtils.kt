package com.example.smartbudget.utils

object ValidationUtils {
    fun validateAmount(amount: String): Boolean {
        return amount.toDoubleOrNull()?.let { it > 0 } ?: false
    }

    fun validateCategory(categoryId: Long?): Boolean {
        return categoryId != null && categoryId > 0
    }
}