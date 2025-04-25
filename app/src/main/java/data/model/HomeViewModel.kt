package data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class HomeViewModel : ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal> = _meal

    init {
        fetchRandomMeal()
    }

    fun fetchRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    response.body()?.meals?.firstOrNull()?.let {
                        _meal.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failed: ${t.message}")
            }
        })
    }
}

