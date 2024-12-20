package com.example.contactsproject.data.local

import androidx.lifecycle.LiveData
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.usecase.GetContactDetail

interface LocalDataSource {
    fun getAllContacts(): LiveData<List<ContactListItem>>
    fun getContactById(id:String):LiveData<ContactListItem>
    suspend fun insertOrUpdateContacts(contacts: List<ContactListItem>)

    fun getContactDetailByID(id: String): LiveData<ContactEntity?>
    suspend fun insertOrUpdateContactDetail(productEntity: ContactEntity)
    suspend fun updateContactDetail(contactDetail: ContactEntity)
}