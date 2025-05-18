package id.co.brainy.data.network.response

import com.google.gson.annotations.SerializedName

data class RegistResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String
)

data class LoginResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("token")
    val token: String

)




