package com.example.contactsproject.presentation.common

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Failure(val exception: Throwable) : UIState<Nothing>()
}