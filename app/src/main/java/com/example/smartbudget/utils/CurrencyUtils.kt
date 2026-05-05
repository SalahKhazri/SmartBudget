package com.example.smartbudget.utils

import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    private val formatter = NumberFormat.getCurrencyInstance(Locale("fr", "MA"))

    fun format(amount: Double, currency: String = "MAD"): String {
        return when (currency) {
            "MAD" -> String.format("%.2f DH", amount)
            "EUR" -> String.format("%.2f €", amount)
            "USD" -> String.format("%.2f $", amount)
            else -> String.format("%.2f %s", amount, currency)
        }
    }

    fun formatWithLocale(amount: Double): String {
        return formatter.format(amount)
    }
}