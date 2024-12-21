package com.example.contactsproject.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.contactsproject.data.local.dao.ContactDao
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem

class LocalDataSourceImp(private val contactDao: ContactDao) : LocalDataSource {
    override fun getAllContacts(): LiveData<List<ContactListItem>> {
        return try {
            contactDao.getContacts()
        } catch (e: Exception) {
            throw Exception("Failed to load contacts.$e")
        }
    }

    override fun getContactById(id: String): LiveData<ContactListItem> {
        return contactDao.getContactByID(id)
    }

    override suspend fun insertOrUpdateContacts(contacts: List<ContactListItem>) {
        contactDao.insertOrUpdateContacts(contacts)

    }


    override fun getContactDetailByID(id: String): LiveData<ContactEntity?> {
        return contactDao.getContactDetailByID(id)
    }

    override suspend fun insertOrUpdateContactDetail(contactEntity: ContactEntity) =
        contactDao.insertOrUpdateContactDetail(contactEntity)

    override suspend fun updateContactDetail(contactDetail: ContactEntity) {
        contactDao.updateContactDetails(contactDetail)
    }

    override suspend fun clearAllContacts() {
        contactDao.deleteAllContacts()
    }


}