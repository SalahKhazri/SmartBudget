package com.example.smartbudget.domain.repository

import com.example.smartbudget.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpensesByMonth(month: String): Flow<List<Expense>>
    fun getExpenseById(id: Long): Flow<Expense?>
    suspend fun addExpense(expense: Expense): Long
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(id: Long)
    fun getTotalByMonth(month: String): Flow<Double>
    fun getTotalByCategoryAndMonth(categoryId: Long, month: String): Flow<Double>
}

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getActiveCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Long)
    suspend fun getCategoryById(id: Long): Category?
}

interface MonthlyBudgetRepository {
    fun getBudgetsByMonth(month: String): Flow<List<MonthlyBudget>>
    suspend fun addBudget(budget: MonthlyBudget)
    suspend fun updateBudget(budget: MonthlyBudget)
    suspend fun deleteBudget(id: Long)
}