package com.example.smartbudget.ui.screens.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbudget.domain.model.Category        // ← IMPORT AJOUTÉ
import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.CategoryRepository
import com.example.smartbudget.domain.repository.ExpenseRepository
import com.example.smartbudget.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    private val _sortBy = MutableStateFlow(SortOption.DATE_DESC)

    private val expensesFlow = _currentMonth.flatMapLatest { month ->
        expenseRepository.getExpensesByMonth(month.toString())
    }

    private val totalFlow = _currentMonth.flatMapLatest { month ->
        expenseRepository.getTotalByMonth(month.toString())
    }

    val uiState: StateFlow<ExpensesUiState> = combine(
        _currentMonth,
        expensesFlow,
        categoryRepository.getActiveCategories(),
        totalFlow,
        _selectedCategoryId,
        _sortBy
    ) { values: Array<Any?> ->
        @Suppress("UNCHECKED_CAST")
        val month = values[0] as YearMonth
        val expenses = values[1] as List<Expense>
        val categories = values[2] as List<Category>
        val total = values[3] as Double
        val catId = values[4] as Long?
        val sort = values[5] as SortOption

        val filtered = catId?.let { id -> expenses.filter { it.categoryId == id } } ?: expenses
        val sorted = when (sort) {
            SortOption.DATE_DESC -> filtered.sortedByDescending { it.date }
            SortOption.DATE_ASC -> filtered.sortedBy { it.date }
            SortOption.AMOUNT_DESC -> filtered.sortedByDescending { it.amount }
            SortOption.AMOUNT_ASC -> filtered.sortedBy { it.amount }
        }

        ExpensesUiState(
            expenses = sorted,
            categories = categories,
            currentMonth = DateUtils.formatMonthYear(month),
            totalMonth = total,
            selectedCategoryId = catId,
            sortBy = sort
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExpensesUiState()
    )

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }

    fun selectCategory(categoryId: Long?) {
        _selectedCategoryId.value = categoryId
    }

    fun setSortOption(option: SortOption) {
        _sortBy.value = option
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense.id)
        }
    }
}