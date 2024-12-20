package com.example.contactsproject.presentation.contactDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.contactsproject.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
init {
    startObservingContacts()
}
    private val _contactId = MutableLiveData<String>()
    val contactItem= _contactId.switchMap {id->
        useCases.getContactItem(id)
    }
    val contactDetail = _contactId.switchMap { id ->
       useCases.getContactDetail(id)
    }

    fun setContactID(id:String) {
        _contactId.value=id
    }


    private fun startObservingContacts() {
        useCases.stopObservingContacts.invoke()
        useCases.observingContacts{
            viewModelScope.launch {
                useCases.contactsChanged.invoke()
                _contactId.value=_contactId.value
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCases.stopObservingContacts()
    }
}
