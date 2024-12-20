package com.example.contactsproject.domain.repository

import androidx.lifecycle.LiveData
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem

interface Repository {
    fun getAllContacts(): LiveData<ResultResponse<List<ContactListItem>>>
    fun getContactItemByID(contactId: String):LiveData<ContactListItem>
    fun getContactDetailByID(contactId: String):LiveData<ResultResponse<ContactEntity?>>
    fun startObservingContacts(onChange: () -> Unit)
    fun stopObservingContacts()
    suspend fun updateLocalContacts()
}