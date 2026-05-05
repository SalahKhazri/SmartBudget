package com.example.smartbudget.domain.usecase.expense

import com.example.smartbudget.domain.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expenseId: Long) {
        require(expenseId > 0) { "ID de dépense invalide" }
        expenseRepository.deleteExpense(expenseId)
    }
}