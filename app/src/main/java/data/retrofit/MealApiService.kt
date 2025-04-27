package data.retrofit

import data.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php?f=a")
    fun getMeals(): Call<MealResponse>

    @GET("search.php")
    fun searchMeals(@Query("s") query: String): Call<MealResponse>
}
