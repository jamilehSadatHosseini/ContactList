package com.example.contactsproject.data.contactObserver

import android.content.ContentResolver
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.ResultResponse

class ContactsObserverDataSourceImp(
    private val contentResolver: ContentResolver
) : BaseDataSource(),ContactsObserverDataSource {

    private var contactObserver: ContactObserver? = null

    override fun startObservingContacts(onContactsChanged: () -> Unit) {
        contactObserver = ContactObserver(contentResolver, onContactsChanged)
        contactObserver?.startObserving()
    }

    override fun stopObservingContacts() {
        contactObserver?.stopObserving()
    }

    override suspend fun getUpdatedContacts(): ResultResponse<List<ContactListItem>> {
        return getResult {
            val contacts = contactObserver?.fetchContacts() ?: emptyList()
            ResultResponse.Success(contacts)
        }
    }

    override suspend fun getContactDetail(id: String): ResultResponse<ContactEntity?> {
        return getResult {
            val contactDetail= contactObserver?.fetchContactById(id)
            ResultResponse.Success(contactDetail)

        }
    }
}
