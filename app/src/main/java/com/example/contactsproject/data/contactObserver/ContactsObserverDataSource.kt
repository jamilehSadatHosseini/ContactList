package com.example.contactsproject.data.contactObserver

import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.ResultResponse

interface ContactsObserverDataSource {
    fun startObservingContacts(onChange: () -> Unit)
    fun stopObservingContacts()
    suspend fun getUpdatedContacts(): ResultResponse<List<ContactListItem>>
    suspend fun getContactDetail(id: String): ResultResponse<ContactEntity?>
}