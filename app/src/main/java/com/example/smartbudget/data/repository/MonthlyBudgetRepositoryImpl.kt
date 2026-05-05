package com.example.smartbudget.data.repository

import com.example.smartbudget.data.local.dao.MonthlyBudgetDao
import com.example.smartbudget.data.mapper.toDomain
import com.example.smartbudget.data.mapper.toEntity
import com.example.smartbudget.domain.model.MonthlyBudget
import com.example.smartbudget.domain.repository.MonthlyBudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonthlyBudgetRepositoryImpl @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao
) : MonthlyBudgetRepository {

    override fun getBudgetsByMonth(month: String): Flow<List<MonthlyBudget>> {
        return monthlyBudgetDao.getBudgetsByMonth(month).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addBudget(budget: MonthlyBudget) {
        monthlyBudgetDao.insert(budget.toEntity())
    }

    override suspend fun updateBudget(budget: MonthlyBudget) {
        monthlyBudgetDao.update(budget.toEntity())
    }

    override suspend fun deleteBudget(id: Long) {
        monthlyBudgetDao.deleteById(id)
    }
}