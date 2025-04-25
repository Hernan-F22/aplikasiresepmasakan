package data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<List<Meal>>()
    val searchResults: LiveData<List<Meal>> = _searchResults

    fun searchMeals(query: String) {
        Log.d("SearchViewModel", "Searching for: $query") // Tambahan debug log

        RetrofitInstance.api.searchMeals(query).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    Log.d("SearchViewModel", "Meals response: $meals")
                    _searchResults.postValue(meals ?: emptyList())
                } else {
                    Log.e("SearchViewModel", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("SearchViewModel", "Network Error: ${t.localizedMessage}")
            }
        })
    }
}