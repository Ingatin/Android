package id.co.brainy.data.repository

import id.co.brainy.data.model.LoginReq
import id.co.brainy.data.model.RegisterReq
import id.co.brainy.data.network.response.LoginResponse
import id.co.brainy.data.network.response.RegistResponse
import id.co.brainy.data.network.retrofit.ApiService
import id.co.brainy.data.utils.parseErrorMessage

class AuthRepository(private val apiService: ApiService){

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<RegistResponse>{
        return try {
            val request = RegisterReq(username,email,password)
            val response = apiService.register(request)
            Result.success(response)
        }catch (e: Exception){
            val message = parseErrorMessage(e)
            Result.failure(Exception(message))
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponse>{
        return try {
            val request = LoginReq(email,password)
            val response = apiService.login(request)
            Result.success(response)
        }catch (e: Exception){
            val message = parseErrorMessage(e)
            Result.failure(Exception(message))
        }
    }


}