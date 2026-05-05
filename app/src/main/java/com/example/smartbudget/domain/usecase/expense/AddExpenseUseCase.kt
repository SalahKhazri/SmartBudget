package com.example.smartbudget.domain.usecase.expense

import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Long {
        require(expense.amount > 0) { "Le montant doit être strictement positif" }
        require(expense.categoryId > 0) { "La catégorie est obligatoire" }
        return expenseRepository.addExpense(expense)
    }
}