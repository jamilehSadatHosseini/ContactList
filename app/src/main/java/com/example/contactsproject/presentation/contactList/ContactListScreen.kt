package com.example.contactsproject.presentation.contactList

import ContactItem
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.contactsproject.R
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.ResultResponse
import com.example.contactsproject.presentation.common.ErrorScreen
import com.example.contactsproject.presentation.navgraph.Route
import com.example.omidpaytask.presentation.common.ProgressIndiProductor


@Composable
fun ContactListScreen(viewModel: ContactListViewModel, navController: NavController) {
    val result by viewModel.contacts.observeAsState()
    val loading by viewModel.loading.observeAsState(false)

    if (loading) {
        ProgressIndiProductor()
    } else {
        when (result) {
            is ResultResponse.Success -> {
                val contacts = (result as ResultResponse.Success<List<ContactListItem>>).data
                LazyColumn {
                    items(contacts, key = { contact -> contact.id }) { contact ->
                        ContactItem(contact = contact, onClick = {
                            navController.navigate(Route.ContactDetailsScreen.route.replace("{contactId}", contact.id))
                        })
                    }
                }
            }
            is ResultResponse.Failure -> {
                val exception = (result as ResultResponse.Failure).exception
               ErrorScreen(onRetryClick = { }, error = exception, message = stringResource(R.string.errorInLoadingContacts) )
            }

            else -> {
                ErrorScreen(onRetryClick = { }, error = null, message = stringResource(R.string.empty) )

            }
        }
    }
}

