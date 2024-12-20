package com.example.contactsproject.data.contactObserver

import com.example.contactsproject.domain.repository.ResultResponse
import java.lang.Exception

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> ResultResponse<T>): ResultResponse<T> {
        return try {
            call()
        } catch (e: Exception) {
            error(e)
        }
    }

    private fun <T> error(exception: Exception): ResultResponse<T> {
        return ResultResponse.Failure(exception)
    }
}
