package com.example.aplikasiresepmasakan.data

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("idMeal")
    val id: String,
    
    @SerializedName("strMeal")
    val title: String,
    
    @SerializedName("strInstructions")
    val description: String,
    
    @SerializedName("strMealThumb")
    val imageUrl: String,
    
    @SerializedName("strCategory")
    val category: String?,
    
    @SerializedName("strArea")
    val area: String?,
    
    @SerializedName("strIngredient1") val ingredient1: String? = null,
    @SerializedName("strIngredient2") val ingredient2: String? = null,
    @SerializedName("strIngredient3") val ingredient3: String? = null,
    @SerializedName("strIngredient4") val ingredient4: String? = null,
    @SerializedName("strIngredient5") val ingredient5: String? = null,
    @SerializedName("strIngredient6") val ingredient6: String? = null,
    @SerializedName("strIngredient7") val ingredient7: String? = null,
    @SerializedName("strIngredient8") val ingredient8: String? = null,
    @SerializedName("strIngredient9") val ingredient9: String? = null,
    @SerializedName("strIngredient10") val ingredient10: String? = null,
    
    @SerializedName("strMeasure1") val measure1: String? = null,
    @SerializedName("strMeasure2") val measure2: String? = null,
    @SerializedName("strMeasure3") val measure3: String? = null,
    @SerializedName("strMeasure4") val measure4: String? = null,
    @SerializedName("strMeasure5") val measure5: String? = null,
    @SerializedName("strMeasure6") val measure6: String? = null,
    @SerializedName("strMeasure7") val measure7: String? = null,
    @SerializedName("strMeasure8") val measure8: String? = null,
    @SerializedName("strMeasure9") val measure9: String? = null,
    @SerializedName("strMeasure10") val measure10: String? = null
) {
    fun getIngredientsList(): List<String> {
        val ingredients = mutableListOf<String>()
        
        // Fungsi helper untuk menambahkan bahan jika keduanya tidak null
        fun addIngredient(ingredient: String?, measure: String?) {
            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                ingredients.add("$measure $ingredient")
            }
        }
        
        // Menambahkan semua bahan yang ada
        addIngredient(ingredient1, measure1)
        addIngredient(ingredient2, measure2)
        addIngredient(ingredient3, measure3)
        addIngredient(ingredient4, measure4)
        addIngredient(ingredient5, measure5)
        addIngredient(ingredient6, measure6)
        addIngredient(ingredient7, measure7)
        addIngredient(ingredient8, measure8)
        addIngredient(ingredient9, measure9)
        addIngredient(ingredient10, measure10)
        
        return ingredients
    }
} 