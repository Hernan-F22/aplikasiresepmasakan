package com.example.aplikasiresepmasakan.ui.fragments

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
import com.example.aplikasiresepmasakan.ui.RecipeDetailActivity
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        setupRecyclerView()
        setupObservers()
        loadRecipes()

        swipeRefreshLayout.setOnRefreshListener {
            loadRecipes()
        }
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
            // Ketika resep diklik, buka detail
            startActivity(
                Intent(requireContext(), RecipeDetailActivity::class.java)
                    .putExtra("recipeId", recipe.id)
            )
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
        }
    }

    private fun setupObservers() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateRecipes(recipes)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun loadRecipes() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.searchRecipes("") // Kosong untuk mendapatkan semua resep
    }
} 