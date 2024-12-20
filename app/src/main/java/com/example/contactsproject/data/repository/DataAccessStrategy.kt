package com.example.contactsproject.data.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.contactsproject.domain.repository.ResultResponse
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    call: suspend () -> ResultResponse<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<ResultResponse<T>> =
    liveData(Dispatchers.IO) {
        val source = databaseQuery.invoke().map { ResultResponse.Success(it) as ResultResponse<T> }
        emitSource(source)

        val responseStatus = call.invoke()
        if (responseStatus is ResultResponse.Success) {
         responseStatus.data?.let {
             saveCallResult(responseStatus.data)
         }
        }
        else if (responseStatus is ResultResponse.Failure) {
            emit(ResultResponse.Failure(responseStatus.exception))
            emitSource(source)
        }
    }

