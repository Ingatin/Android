package id.co.brainy.data.network.retrofit

import id.co.brainy.data.model.LoginReq
import id.co.brainy.data.model.RegisterReq
import id.co.brainy.data.network.response.LoginResponse
import id.co.brainy.data.network.response.RegistResponse
import retrofit2.http.Body
import retrofit2.http.POST

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

//


}