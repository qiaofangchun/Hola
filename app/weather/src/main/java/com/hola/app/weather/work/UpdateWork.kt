package com.hola.app.weather.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hola.app.weather.repository.WeatherRepository

class UpdateWork(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        WeatherRepository.updateWeatherByLoc()
        return Result.success()
    }
}