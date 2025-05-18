package id.co.brainy.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.brainy.data.network.retrofit.ApiConfig
import id.co.brainy.data.repository.AuthRepository
import id.co.brainy.ui.screen.auth.AuthViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val apiService = ApiConfig.getApiService()

        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(AuthRepository((apiService))) as T
            }

            else -> throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
        }


    }

}