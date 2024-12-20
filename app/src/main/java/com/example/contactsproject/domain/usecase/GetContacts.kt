package com.example.contactsproject.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.contactsproject.domain.model.ContactListItem
import com.example.contactsproject.domain.repository.Repository
import com.example.contactsproject.domain.repository.ResultResponse
import javax.inject.Inject

class GetContacts @Inject constructor(private val repository: Repository) {
    operator fun invoke(): LiveData<ResultResponse<List<ContactListItem>>> {
        return repository.getAllContacts()
    }
}