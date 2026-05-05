package com.example.smartbudget.data.local.dao

import androidx.room.*
import com.example.smartbudget.data.local.entity.MonthlyBudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyBudgetDao {
    @Query("SELECT * FROM monthly_budgets WHERE month = :month")
    fun getBudgetsByMonth(month: String): Flow<List<MonthlyBudgetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: MonthlyBudgetEntity)

    @Update
    suspend fun update(budget: MonthlyBudgetEntity)

    @Query("DELETE FROM monthly_budgets WHERE id = :id")
    suspend fun deleteById(id: Long)
}