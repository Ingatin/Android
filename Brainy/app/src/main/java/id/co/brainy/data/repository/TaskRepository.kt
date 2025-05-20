package id.co.brainy.data.repository

import id.co.brainy.data.network.response.TaskRequest
import id.co.brainy.data.network.response.TaskResponse
import id.co.brainy.data.network.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository {
    private val apiService = ApiConfig.getApiService()

    // Create Task (already implemented)
    suspend fun createTask(
        token: String,
        title: String,
        description: String,
        category: String,
        deadline: String
    ): TaskResponse {
        return apiService.createTask(
            token = ApiConfig.getAuthHeader(token),
            request = TaskRequest(title, description, category, deadline)
        )
    }

    // Get All Tasks
    fun getAllTasks(token: String): Flow<List<TaskResponse>> = flow {
        try {
            val response = apiService.getAllTasks(ApiConfig.getAuthHeader(token))
            emit(response)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Get Tasks by Category
    fun getTasksByCategory(token: String, category: String): Flow<List<TaskResponse>> = flow {
        try {
            val response = apiService.getTasksByCategory(
                token = ApiConfig.getAuthHeader(token),
                category = category
            )
            emit(response)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Get Task by ID
    fun getTaskById(token: String, id: String): Flow<TaskResponse?> = flow {
        try {
            val response = apiService.getTaskById(
                token = ApiConfig.getAuthHeader(token),
                id = id
            )
            emit(response)
        } catch (e: Exception) {
            emit(null)
        }
    }
    suspend fun updateTask(
        token: String,
        id: String,
        title: String,
        description: String,
        category: String,
        deadline: String
    ): TaskResponse {
        return apiService.updateTask(
            token = ApiConfig.getAuthHeader(token),
            id = id,
            request = TaskRequest(title, description, category, deadline)
        )
    }

    suspend fun deleteTask(token: String, id: String): Boolean {
        return try {
            apiService.deleteTask(
                token = ApiConfig.getAuthHeader(token),
                id = id
            )
            true
        } catch (e: Exception) {
            false
        }
    }
}