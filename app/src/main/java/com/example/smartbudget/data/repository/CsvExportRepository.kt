package com.example.smartbudget.data.repository

import android.content.Context
import android.os.Environment
import com.example.smartbudget.data.local.dao.ExpenseDao
import com.example.smartbudget.data.mapper.toDomain
import com.opencsv.CSVWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CsvExportRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    @ApplicationContext private val context: Context
) {
    suspend fun exportMonthToCsv(month: String): Result<File> = withContext(Dispatchers.IO) {
        try {
            val expenses = expenseDao.getExpensesByMonth(month).first()

            // Dossier Downloads
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val fileName = "smartbudget_${month}.csv"
            val file = File(downloadsDir, fileName)

            CSVWriter(FileWriter(file)).use { writer ->
                // En-tête
                writer.writeNext(arrayOf("Date", "Montant", "Devise", "Categorie_ID", "Note", "Methode_Paiement"))

                // Données
                expenses.forEach { expense ->
                    writer.writeNext(arrayOf(
                        expense.date,
                        expense.amount.toString(),
                        expense.currency,
                        expense.categoryId.toString(),
                        expense.note,
                        expense.paymentMethod
                    ))
                }
            }

            Result.success(file)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}