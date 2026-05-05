package com.example.smartbudget.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.smartbudget.data.local.dao.*
import com.example.smartbudget.data.local.entity.*
import com.example.smartbudget.domain.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, MonthlyBudgetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SmartBudgetDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao

    companion object {
        @Volatile
        private var INSTANCE: SmartBudgetDatabase? = null

        fun getDatabase(context: Context): SmartBudgetDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SmartBudgetDatabase::class.java,
                    "smartbudget_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insertion des catégories par défaut
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val categoryDao = database.categoryDao()
                    Category.DEFAULT_CATEGORIES.forEach { category ->
                        categoryDao.insert(
                            CategoryEntity(
                                id = category.id,
                                name = category.name,
                                icon = category.icon,
                                color = category.color,
                                isActive = true
                            )
                        )
                    }
                }
            }
        }
    }
}