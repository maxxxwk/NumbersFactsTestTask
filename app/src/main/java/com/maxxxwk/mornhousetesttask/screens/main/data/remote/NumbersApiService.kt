package com.maxxxwk.mornhousetesttask.screens.main.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApiService {
    @GET("/{number}")
    suspend fun getFactAboutNumber(@Path("number") number: Int): String

    @GET("/random/math")
    suspend fun getFactAboutRandomNumber(): String
}
