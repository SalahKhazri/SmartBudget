package com.example.smartbudget.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbudget.domain.repository.CategoryRepository
import com.example.smartbudget.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.YearMonth
import javax.inject.Inject

data class CategoryTotal(
    val categoryId: Long,
    val categoryName: String,
    val categoryIcon: String,
    val total: Double
)

data class StatsUiState(
    val currentMonth: String = "",
    val totalMonth: Double = 0.0,
    val categoryTotals: List<CategoryTotal> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _currentMonth = MutableStateFlow(YearMonth.now())

    val uiState: StateFlow<StatsUiState> = combine(
        _currentMonth,
        expenseRepository.getTotalByMonth(_currentMonth.value.toString())
    ) { month, total ->
        StatsUiState(
            currentMonth = month.toString(),
            totalMonth = total
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState()
    )
}