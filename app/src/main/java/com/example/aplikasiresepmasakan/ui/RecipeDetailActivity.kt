package com.example.aplikasiresepmasakan.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aplikasiresepmasakan.R
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel

class RecipeDetailActivity : AppCompatActivity() {
    private val viewModel: RecipeViewModel by viewModels()
    
    private lateinit var recipeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var ingredientsTextView: TextView
    private lateinit var instructionsTextView: TextView
    private lateinit var loadingView: View

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Inisialisasi views
        recipeImageView = findViewById(R.id.recipeImageView)
        titleTextView = findViewById(R.id.titleTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        ingredientsTextView = findViewById(R.id.ingredientsTextView)
        instructionsTextView = findViewById(R.id.instructionsTextView)
        loadingView = findViewById(R.id.loadingView)

        // Tampilkan loading
        showLoading(true)

        val recipeId = intent.getStringExtra("recipeId")
        recipeId?.let {
            viewModel.getRecipeDetail(it)
        }

        viewModel.selectedRecipe.observe(this) { recipe ->
            showLoading(false)
            recipe?.let {
                // Set judul
                title = it.title
                titleTextView.text = it.title
                
                // Set kategori dan area
                val categoryText = buildString {
                    append(it.category ?: "")
                    it.area?.let { area ->
                        if (isNotEmpty() && area.isNotEmpty()) {
                            append(" • ")
                        }
                        append(area)
                    }
                }
                categoryTextView.text = categoryText
                
                // Load gambar
                Glide.with(this)
                    .load(it.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(recipeImageView)

                // Set bahan-bahan
                val ingredients = it.getIngredientsList()
                if (ingredients.isNotEmpty()) {
                    val ingredientsText = ingredients.joinToString("\n") { ingredient ->
                        "• $ingredient"
                    }
                    ingredientsTextView.text = ingredientsText
                } else {
                    ingredientsTextView.text = "Tidak ada bahan yang tercantum"
                }

                // Set instruksi
                instructionsTextView.text = it.description
            }
        }
    }

    private fun showLoading(show: Boolean) {
        loadingView.visibility = if (show) View.VISIBLE else View.GONE
    }
} 