package com.example.aplikasiresepmasakan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasiresepmasakan.api.RetrofitClient
import com.example.aplikasiresepmasakan.data.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _selectedRecipe = MutableLiveData<Recipe>()
    val selectedRecipe: LiveData<Recipe> = _selectedRecipe

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.searchMeals(query)
                _recipes.value = response.meals
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getRecipeDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getMealById(id)
                _selectedRecipe.value = response.meals.firstOrNull()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
} 