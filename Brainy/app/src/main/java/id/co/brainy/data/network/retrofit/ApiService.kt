package id.co.brainy.data.network.retrofit

import id.co.brainy.data.model.LoginReq
import id.co.brainy.data.model.RegisterReq
import id.co.brainy.data.network.response.LoginResponse
import id.co.brainy.data.network.response.RegistResponse
import id.co.brainy.data.network.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
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


}