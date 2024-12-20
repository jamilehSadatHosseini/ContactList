package com.example.contactsproject.domain.usecase

data class UseCases(
    val getContacts: GetContacts,
    val getContactItem:GetContactItem,
    val getContactDetail: GetContactDetail,
    val contactsChanged: ContactsChanged,
    val observingContacts: ObserveContacts,
    val stopObservingContacts: StopObservingContacts
)
