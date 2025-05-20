package id.co.brainy.data.network.retrofit

import id.co.brainy.data.model.LoginReq
import id.co.brainy.data.model.RegisterReq
import id.co.brainy.data.network.response.LoginResponse
import id.co.brainy.data.network.response.RegistResponse
import id.co.brainy.data.network.response.UserResponse
import id.co.brainy.data.network.response.TaskResponse
import id.co.brainy.data.network.response.TaskRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


//    Auth
    @POST("auth/reg")
    suspend fun register(
        @Body request: RegisterReq
    ): RegistResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginReq
    ): LoginResponse

    @GET("auth/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): UserResponse

//  Task
@POST("tasks")
suspend fun createTask(
    @Header("Authorization") token: String,
    @Body request: TaskRequest
): TaskResponse

    @GET("tasks")
    suspend fun getAllTasks(
        @Header("Authorization") token: String
    ): List<TaskResponse>

    @GET("tasks/category/{category}")
    suspend fun getTasksByCategory(
        @Header("Authorization") token: String,
        @Path("category") category: String
    ): List<TaskResponse>

    @GET("tasks/{id}")
    suspend fun getTaskById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): TaskResponse

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: TaskRequest
    ): TaskResponse

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): TaskResponse

}