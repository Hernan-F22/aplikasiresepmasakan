package com.example.aplikasiresepmasakan.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiresepmasakan.R
import com.example.aplikasiresepmasakan.adapter.RecipeAdapter
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel

class RecipeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.recipes.observe(this) { recipes ->
            recyclerView.adapter = RecipeAdapter(recipes) { recipe ->
                // Ketika item diklik, buka detail resep
                startActivity(
                    Intent(this, RecipeDetailActivity::class.java)
                        .putExtra("recipeId", recipe.id)
                )
            }
        }

        // Load resep
        viewModel.searchRecipes("chicken") // Contoh pencarian resep ayam
    }
} 