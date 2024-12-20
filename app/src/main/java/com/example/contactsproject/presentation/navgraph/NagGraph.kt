package com.example.contactsproject.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactsproject.presentation.contactDetail.ContactDetailViewModel
import com.example.contactsproject.presentation.contactDetail.ContactDetailsScreen
import com.example.contactsproject.presentation.contactList.ContactListScreen
import com.example.contactsproject.presentation.contactList.ContactListViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination)
    {

        composable(route = Route.ContactListScreen.route) {
            val viewModel: ContactListViewModel = hiltViewModel()
            ContactListScreen(viewModel = viewModel, navController)
        }
        composable(route = Route.ContactDetailsScreen.route) { backStackEntry ->
            val viewModel: ContactDetailViewModel = hiltViewModel()
            val contactId = backStackEntry.arguments?.getString("contactId")
            if (contactId != null) {
                ContactDetailsScreen(contactId = contactId, viewModel = viewModel)
            }
        }
    }
}
