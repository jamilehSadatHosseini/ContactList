package com.example.contactsproject.presentation.navgraph


sealed class Route(val route: String) {
    object ContactListScreen : Route("ContactListScreen")
    object ContactDetailsScreen : Route("ContactDetailsScreen/{contactId}")
}
