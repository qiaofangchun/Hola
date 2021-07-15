/*
package com.hola.demo

import android.os.Looper
import android.util.Log
import com.hola.architecture.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovieUseCase(val coroutineScope: CoroutineScope) : UseCase<String, String>() {

    private fun isMain():Boolean{
        return Looper.getMainLooper() == Looper.myLooper()
    }

    override suspend fun run(params: String?): String {
        coroutineScope.launch {

        }
        return try {
            ApiService.get().movies()
        } catch (e: Exception) {
            e.message ?: "message is null"
        }
    }
}*/
