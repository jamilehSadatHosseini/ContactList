package com.example.contactsproject.presentation.contactList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsproject.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    val contacts = useCases.getContacts()
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        startObservingContacts()
    }

    private fun startObservingContacts() {
        useCases.observingContacts {
            viewModelScope.launch {
                useCases.contactsChanged.invoke()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCases.stopObservingContacts()
    }
}
