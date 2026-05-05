package com.example.smartbudget.data.mapper

import com.example.smartbudget.data.local.entity.*
import com.example.smartbudget.domain.model.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

// Expense Mappers
fun ExpenseEntity.toDomain() = Expense(
    id = id,
    amount = amount,
    currency = currency,
    date = LocalDate.parse(date, dateFormatter),
    categoryId = categoryId,
    note = note,
    paymentMethod = PaymentMethod.valueOf(paymentMethod),
    createdAt = LocalDateTime.parse(createdAt, dateTimeFormatter),
    updatedAt = LocalDateTime.parse(updatedAt, dateTimeFormatter)
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    amount = amount,
    currency = currency,
    date = date.format(dateFormatter),
    categoryId = categoryId,
    note = note,
    paymentMethod = paymentMethod.name,
    createdAt = createdAt.format(dateTimeFormatter),
    updatedAt = updatedAt.format(dateTimeFormatter)
)

// Category Mappers
fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    icon = icon,
    color = color,
    isActive = isActive
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    icon = icon,
    color = color,
    isActive = isActive
)

// MonthlyBudget Mappers
fun MonthlyBudgetEntity.toDomain() = MonthlyBudget(
    id = id,
    month = month,
    categoryId = categoryId,
    limitAmount = limitAmount
)

fun MonthlyBudget.toEntity() = MonthlyBudgetEntity(
    id = id,
    month = month,
    categoryId = categoryId,
    limitAmount = limitAmount
)