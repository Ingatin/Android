package id.co.brainy.data.network.retrofit

import id.co.brainy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {

    private const val BASE_URL = "https://brainy-api-259318012988.asia-southeast2.run.app/api/"

//    fun setToken(token: String?) {
//        currentToken = token
//    }


    fun getApiService(): ApiService{

        val loggingInterceptor =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

//        val authInterceptor = Interceptor { chain ->
//            val req = chain.request()
//            val accessToken = currentToken
//            val requestHeaders = req.newBuilder()
//                .addHeader("Authorization", "Bearer $accessToken")
//                .build()
//            chain.proceed(requestHeaders)
//        }

        val client:OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)

    }


}