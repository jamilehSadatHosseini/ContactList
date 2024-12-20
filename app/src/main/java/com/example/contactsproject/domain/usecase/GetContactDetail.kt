package com.example.contactsproject.domain.usecase

import androidx.lifecycle.LiveData
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.repository.Repository
import javax.inject.Inject

class GetContactDetail @Inject constructor(private val repository: Repository)
{
     operator fun invoke(contactId: String) = repository.getContactDetailByID(contactId)
}