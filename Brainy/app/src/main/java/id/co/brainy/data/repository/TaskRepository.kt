package id.co.brainy.data.repository

import id.co.brainy.data.network.response.TasksItem
import id.co.brainy.data.network.retrofit.ApiConfig
import id.co.brainy.data.network.retrofit.ApiService
import id.co.brainy.data.utils.UserPreferences
import id.co.brainy.data.utils.parseErrorMessage
import kotlinx.coroutines.flow.first

class TaskRepository(private val apiService: ApiService, private val userPreferences: UserPreferences) {

    suspend fun getAllTasks(): Result<List<TasksItem>?>{
        return try {
            val token = userPreferences.getToken().first()
            val userId = userPreferences.getUserId().first()

            if (token.isNullOrEmpty() || userId.isNullOrEmpty()) {
                return Result.failure(Exception("Token atau UserId kosong"))
            }

            val response = apiService.getAllTasks(ApiConfig.getAuthHeader(token), userId)
            Result.success(response.tasks)
        } catch (e: Exception){
            val message = parseErrorMessage(e)
            Result.failure(Exception(message))
        }
    }



}