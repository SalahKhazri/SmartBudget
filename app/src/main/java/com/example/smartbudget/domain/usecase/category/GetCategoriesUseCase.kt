package com.example.smartbudget.domain.usecase.category

import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(onlyActive: Boolean = true): Flow<List<Category>> {
        return if (onlyActive) {
            categoryRepository.getActiveCategories()
        } else {
            categoryRepository.getAllCategories()
        }
    }
}