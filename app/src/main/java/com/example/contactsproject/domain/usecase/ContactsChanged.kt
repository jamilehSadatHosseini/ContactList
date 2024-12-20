package com.example.contactsproject.domain.usecase

import com.example.contactsproject.domain.repository.Repository
import javax.inject.Inject

class ContactsChanged @Inject constructor(private val repository: Repository) {
        suspend operator fun invoke() {
                repository.updateLocalContacts()
        }
}