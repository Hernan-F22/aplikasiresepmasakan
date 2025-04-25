package data.retrofit

import data.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("random.php")
    fun getRandomMeal(): Call<MealResponse>

    @GET("search.php")
    fun searchMeals(@Query("s") query: String): Call<MealResponse>
}
