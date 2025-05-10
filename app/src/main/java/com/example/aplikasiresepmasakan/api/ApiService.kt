package com.example.aplikasiresepmasakan.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): RecipeResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): RecipeResponse
} 