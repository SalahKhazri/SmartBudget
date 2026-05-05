package com.example.smartbudget.di

import android.content.Context
import com.example.smartbudget.data.local.SmartBudgetDatabase
import com.example.smartbudget.data.local.dao.*
import com.example.smartbudget.data.repository.*
import com.example.smartbudget.domain.repository.*
import com.example.smartbudget.domain.repository.MonthlyBudgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SmartBudgetDatabase {
        return SmartBudgetDatabase.getDatabase(context)
    }

    @Provides
    fun provideExpenseDao(database: SmartBudgetDatabase) = database.expenseDao()

    @Provides
    fun provideCategoryDao(database: SmartBudgetDatabase) = database.categoryDao()

    @Provides
    fun provideMonthlyBudgetDao(database: SmartBudgetDatabase) = database.monthlyBudgetDao()

    @Provides
    @Singleton
    fun provideExpenseRepository(impl: ExpenseRepositoryImpl): ExpenseRepository = impl

    @Provides
    @Singleton
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository = impl

    @Provides
    @Singleton
    fun provideMonthlyBudgetRepository(impl: MonthlyBudgetRepositoryImpl): MonthlyBudgetRepository = impl

    @Provides
    @Singleton
    fun provideCsvExportRepository(
        expenseDao: ExpenseDao,
        @ApplicationContext context: Context
    ): CsvExportRepository = CsvExportRepository(expenseDao, context)
}