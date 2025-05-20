package id.co.brainy.data.network.response

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)

data class TaskRequest(
    val title: String,
    val description: String,
    val category: String,
    val deadline: String
)