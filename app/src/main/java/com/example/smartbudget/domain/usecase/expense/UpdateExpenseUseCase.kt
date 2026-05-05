package com.example.smartbudget.domain.usecase.expense

import com.example.smartbudget.domain.model.Expense
import com.example.smartbudget.domain.repository.ExpenseRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        require(expense.id > 0) { "ID de dépense invalide" }
        require(expense.amount > 0) { "Le montant doit être strictement positif" }
        require(expense.categoryId > 0) { "La catégorie est obligatoire" }
        expenseRepository.updateExpense(expense)
    }
}