package com.example.smartbudget.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartbudget.ui.screens.addedit.AddEditExpenseScreen
import com.example.smartbudget.ui.screens.expenses.ExpensesScreen
import com.example.smartbudget.ui.screens.settings.SettingsScreen
import com.example.smartbudget.ui.screens.stats.StatsScreen

sealed class Screen(val route: String) {
    object Expenses : Screen("expenses")
    object AddEditExpense : Screen("add_edit_expense?expenseId={expenseId}") {
        fun createRoute(expenseId: Long? = null) =
            "add_edit_expense?expenseId=$expenseId"
    }
    object Stats : Screen("stats")
    object Settings : Screen("settings")
}

@Composable
fun SmartBudgetNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Expenses.route,
        modifier = modifier
    ) {
        composable(Screen.Expenses.route) {
            ExpensesScreen(
                onAddExpense = { navController.navigate(Screen.AddEditExpense.createRoute()) },
                onEditExpense = { id ->
                    navController.navigate(Screen.AddEditExpense.createRoute(id))
                }
            )
        }
        composable(Screen.AddEditExpense.route) { backStackEntry ->
            val expenseId = backStackEntry.arguments
                ?.getString("expenseId")
                ?.toLongOrNull()
            AddEditExpenseScreen(
                expenseId = expenseId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Stats.route) { StatsScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}