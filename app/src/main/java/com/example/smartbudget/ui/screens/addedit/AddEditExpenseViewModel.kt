package com.example.smartbudget.ui.screens.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.CategoryRepository
import com.example.smartbudget.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddEditUiState(
    val amount: String = "",
    val amountError: String? = null,
    val selectedCategory: Category? = null,
    val categoryError: String? = null,
    val date: LocalDate = LocalDate.now(),
    val note: String = "",
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()

    private var currentExpenseId: Long = 0

    init {
        viewModelScope.launch {
            categoryRepository.getActiveCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun loadExpense(id: Long) {
        viewModelScope.launch {
            expenseRepository.getExpenseById(id).collect { expense ->
                expense?.let {
                    currentExpenseId = it.id
                    _uiState.update { state ->
                        state.copy(
                            amount = it.amount.toString(),
                            selectedCategory = state.categories.find { c -> c.id == it.categoryId },
                            date = it.date,
                            note = it.note
                        )
                    }
                }
            }
        }
    }

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amount = value, amountError = null) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(selectedCategory = category, categoryError = null) }
    }

    fun onDateChange(date: LocalDate) {
        _uiState.update { it.copy(date = date) }
    }

    fun onNoteChange(note: String) {
        _uiState.update { it.copy(note = note) }
    }

    fun saveExpense() {
        val state = _uiState.value
        var hasError = false

        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(amountError = "Montant invalide") }
            hasError = true
        }

        if (state.selectedCategory == null) {
            _uiState.update { it.copy(categoryError = "Catégorie obligatoire") }
            hasError = true
        }

        if (hasError) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val expense = Expense(
                    id = currentExpenseId,
                    amount = amount!!,
                    date = state.date,
                    categoryId = state.selectedCategory!!.id,
                    note = state.note
                )

                if (currentExpenseId == 0L) {
                    expenseRepository.addExpense(expense)
                } else {
                    expenseRepository.updateExpense(expense)
                }
                _uiState.update { it.copy(isSaved = true, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}