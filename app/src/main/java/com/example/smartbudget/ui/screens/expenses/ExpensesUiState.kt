package com.example.smartbudget.ui.screens.expenses

import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.domain.model.Expense

data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    val categories: List<Category> = emptyList(),
    val currentMonth: String = "",
    val totalMonth: Double = 0.0,
    val selectedCategoryId: Long? = null,
    val sortBy: SortOption = SortOption.DATE_DESC,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class SortOption {
    DATE_DESC, DATE_ASC, AMOUNT_DESC, AMOUNT_ASC
}