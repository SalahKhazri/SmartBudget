package com.example.smartbudget.domain.usecase.expense

import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(month: String): Flow<List<Expense>> {
        return expenseRepository.getExpensesByMonth(month)
    }
}