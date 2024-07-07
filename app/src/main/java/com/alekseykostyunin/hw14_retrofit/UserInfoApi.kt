package com.alekseykostyunin.hw14_retrofit

import com.alekseykostyunin.hw14_retrofit.user.Results
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://randomuser.me/"

object Retrofit {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userInfoApi: UserInfoApi = retrofit.create(UserInfoApi::class.java)
}

interface UserInfoApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("api/")
    suspend fun getUserInfo(): Response<Results>
}