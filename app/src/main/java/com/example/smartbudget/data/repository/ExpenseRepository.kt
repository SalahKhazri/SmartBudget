package com.example.smartbudget.data.repository

import com.example.smartbudget.data.local.dao.ExpenseDao
import com.example.smartbudget.data.mapper.toDomain
import com.example.smartbudget.data.mapper.toEntity
import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getExpensesByMonth(month: String): Flow<List<Expense>> {
        return expenseDao.getExpensesByMonth(month).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getExpenseById(id: Long): Flow<Expense?> {
        return expenseDao.getExpenseById(id).map { it?.toDomain() }
    }

    override suspend fun addExpense(expense: Expense): Long {
        return expenseDao.insert(expense.toEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.update(expense.toEntity())
    }

    override suspend fun deleteExpense(id: Long) {
        expenseDao.deleteById(id)
    }

    override fun getTotalByMonth(month: String): Flow<Double> {
        return expenseDao.getTotalByMonth(month)
    }

    override fun getTotalByCategoryAndMonth(categoryId: Long, month: String): Flow<Double> {
        return expenseDao.getTotalByCategoryAndMonth(categoryId, month)
    }
}