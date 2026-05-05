package com.example.smartbudget.data.repository

import com.example.smartbudget.data.local.dao.CategoryDao
import com.example.smartbudget.data.mapper.toDomain
import com.example.smartbudget.data.mapper.toEntity
import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getActiveCategories(): Flow<List<Category>> {
        return categoryDao.getActiveCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addCategory(category: Category): Long {
        return categoryDao.insert(category.toEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.update(category.toEntity())
    }

    override suspend fun deleteCategory(id: Long) {
        val expenseCount = categoryDao.getExpenseCountForCategory(id)
        if (expenseCount > 0) {
            throw IllegalStateException("Impossible de supprimer : des dépenses existent dans cette catégorie")
        }
        categoryDao.deleteById(id)
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getById(id)?.toDomain()
    }
}