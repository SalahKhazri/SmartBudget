package com.example.smartbudget.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbudget.data.repository.CsvExportRepository
import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

data class SettingsUiState(
    val categories: List<Category> = emptyList(),
    val currency: String = "MAD",
    val isLoading: Boolean = false,
    val exportSuccess: String? = null,
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val csvExportRepository: CsvExportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun toggleCategoryActive(categoryId: Long) {
        viewModelScope.launch {
            val category = categoryRepository.getCategoryById(categoryId)
            category?.let {
                categoryRepository.updateCategory(it.copy(isActive = !it.isActive))
            }
        }
    }

    fun addCategory(name: String, icon: String) {
        viewModelScope.launch {
            try {
                val category = Category(
                    name = name,
                    icon = icon,
                    color = "#6C63FF"
                )
                categoryRepository.addCategory(category)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun exportToCsv() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, exportSuccess = null, error = null) }

            val currentMonth = YearMonth.now().toString()

            csvExportRepository.exportMonthToCsv(currentMonth)
                .onSuccess { file ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            exportSuccess = "Exporté : ${file.absolutePath}"
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Erreur : ${e.message}"
                        )
                    }
                }
        }
    }
    fun clearMessages() {
        _uiState.update { it.copy(exportSuccess = null, error = null) }
    }
}