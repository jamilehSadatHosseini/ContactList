package com.example.contactsproject.domain.repository

sealed class ResultResponse<out T> {
    data class Success<out T>(val data: T) : ResultResponse<T>()
    data class Failure(val exception: Throwable) : ResultResponse<Nothing>()
}
