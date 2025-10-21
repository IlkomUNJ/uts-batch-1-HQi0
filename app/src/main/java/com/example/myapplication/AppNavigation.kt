package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            ListContactScreen(navController = navController)
        }

        composable(route = "add_contact") {
            ContactFormScreen(navController = navController, contactId = null)
        }

        composable(
            route = "edit_contact/{contactId}",
            arguments = listOf(navArgument("contactId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null) {
                ContactFormScreen(navController = navController, contactId = contactId)
            } else {
                navController.popBackStack()
            }
        }
    }
}