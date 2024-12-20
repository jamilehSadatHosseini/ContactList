package com.example.contactsproject.domain.usecase

import com.example.contactsproject.domain.repository.Repository
import javax.inject.Inject

class ObserveContacts @Inject constructor(private val repository: Repository) {
    operator fun invoke(onChange: () -> Unit) {
        repository.startObservingContacts {
           onChange()
        }
    }
}