package com.example.contactsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.contactsproject.data.contactObserver.ContactsObserverDataSource
import com.example.contactsproject.data.local.LocalDataSource
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.Repository
import com.example.contactsproject.domain.repository.ResultResponse

class RepositoryImp(
    private val localDataSource: LocalDataSource,
    private val contactObserver: ContactsObserverDataSource
) : Repository {

    override fun getAllContacts(): LiveData<ResultResponse<List<ContactListItem>>> {
        return performGetOperation(
            databaseQuery = { localDataSource.getAllContacts() },
            call = { contactObserver.getUpdatedContacts() },
            saveCallResult = { localDataSource.insertOrUpdateContacts(it) }
        )
    }

    override fun getContactItemByID(contactId: String): LiveData<ContactListItem> {
        return localDataSource.getContactById(contactId)
    }


    override fun getContactDetailByID(contactId: String) = performGetOperation(
        databaseQuery = { localDataSource.getContactDetailByID(contactId) },
        call = { contactObserver.getContactDetail(contactId) },
        saveCallResult = { result -> result?.let { localDataSource.updateContactDetail(it) } }
    )


    override fun startObservingContacts(onChange: () -> Unit) {
        contactObserver.startObservingContacts(onChange)
    }

    override fun stopObservingContacts() {
        contactObserver.stopObservingContacts()

    }

    override suspend fun updateLocalContacts() {
        when (val newContacts = contactObserver.getUpdatedContacts()) {
            is ResultResponse.Success -> localDataSource.insertOrUpdateContacts(newContacts.data)
            is ResultResponse.Failure -> ResultResponse.Failure(newContacts.exception)

        }
    }
}