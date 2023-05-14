package com.msayeh.asteroid.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.msayeh.asteroid.database.AsteroidsDatabase
import com.msayeh.asteroid.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidsDatabase.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)

        return try {
            repository.updateCaching()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}