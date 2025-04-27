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
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun fetchMeals() {
        RetrofitInstance.api.getMeals().enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    _meals.value = meals  // Update LiveData dengan data meal
                } else {
                    Log.e("HomeViewModel", "Error fetching meals: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failed to fetch meals: ${t.message}")
            }
        })
    }
}
