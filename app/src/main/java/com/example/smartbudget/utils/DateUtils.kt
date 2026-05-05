package com.example.smartbudget.utils

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)

    fun formatMonthYear(yearMonth: YearMonth): String {
        return yearMonth.format(formatter).replaceFirstChar { it.uppercase() }
    }
}