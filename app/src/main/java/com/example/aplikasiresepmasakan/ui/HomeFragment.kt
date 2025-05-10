package com.example.aplikasiresepmasakan.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aplikasiresepmasakan.R
import com.example.aplikasiresepmasakan.adapter.RecipeAdapter
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel

class HomeFragment : Fragment() {
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var loadingLayout: View
    private lateinit var emptyLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi views
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        loadingLayout = view.findViewById(R.id.loadingLayout)
        emptyLayout = view.findViewById(R.id.emptyLayout)

        // Setup RecyclerView
        recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
            // Ketika resep diklik, buka detail
            startActivity(
                Intent(requireContext(), RecipeDetailActivity::class.java)
                    .putExtra("recipeId", recipe.id)
            )
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }

        // Observe recipes
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            loadingLayout.visibility = View.GONE
            if (recipes.isEmpty()) {
                emptyLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recipeAdapter = RecipeAdapter(recipes) { recipe ->
                    // Ketika resep diklik, buka detail
                    startActivity(
                        Intent(requireContext(), RecipeDetailActivity::class.java)
                            .putExtra("recipeId", recipe.id)
                    )
                }
                recyclerView.adapter = recipeAdapter
            }
            swipeRefreshLayout.isRefreshing = false
        }

        // Setup SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            loadRecipes()
        }

        // Load initial data
        loadRecipes()
    }

    private fun loadRecipes() {
        loadingLayout.visibility = View.VISIBLE
        emptyLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
        viewModel.searchRecipes("chicken")  // Ganti dengan query yang sesuai
    }
} 