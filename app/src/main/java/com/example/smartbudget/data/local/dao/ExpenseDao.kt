package com.example.smartbudget.data.local.dao

import androidx.room.*
import com.example.smartbudget.data.local.entity.CategoryTotalEntity
import com.example.smartbudget.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("""
        SELECT * FROM expenses 
        WHERE strftime('%Y-%m', date) = :month 
        ORDER BY date DESC, createdAt DESC
    """)
    fun getExpensesByMonth(month: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Long): Flow<ExpenseEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity): Long

    @Update
    suspend fun update(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("""
        SELECT COALESCE(SUM(amount), 0.0) FROM expenses 
        WHERE strftime('%Y-%m', date) = :month
    """)
    fun getTotalByMonth(month: String): Flow<Double>

    @Query("""
        SELECT COALESCE(SUM(amount), 0.0) FROM expenses 
        WHERE categoryId = :categoryId 
        AND strftime('%Y-%m', date) = :month
    """)
    fun getTotalByCategoryAndMonth(categoryId: Long, month: String): Flow<Double>

    @Query("""
        SELECT categoryId, SUM(amount) as total 
        FROM expenses 
        WHERE strftime('%Y-%m', date) = :month 
        GROUP BY categoryId 
        ORDER BY total DESC
    """)
    fun getCategoryTotalsByMonth(month: String): Flow<List<CategoryTotalEntity>>
}