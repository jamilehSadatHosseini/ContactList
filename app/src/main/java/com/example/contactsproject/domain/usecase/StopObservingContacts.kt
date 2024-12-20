package com.example.contactsproject.domain.usecase

import com.example.contactsproject.domain.repository.Repository
import javax.inject.Inject

class StopObservingContacts @Inject constructor(private val repository: Repository) {
    operator fun invoke() {
        repository.stopObservingContacts()
    }
}