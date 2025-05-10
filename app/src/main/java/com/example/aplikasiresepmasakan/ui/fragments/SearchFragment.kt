package com.example.aplikasiresepmasakan.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiresepmasakan.R
import com.example.aplikasiresepmasakan.adapter.RecipeAdapter
import com.example.aplikasiresepmasakan.ui.RecipeDetailActivity
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var searchEditText: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: RecipeViewModel by viewModels()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = view.findViewById(R.id.searchEditText)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        setupRecyclerView()
        setupSearch()
        setupObservers()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
            // Buka detail resep saat diklik
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

    private fun setupSearch() {
        searchEditText.addTextChangedListener { text ->
            // Batalkan pencarian sebelumnya jika ada
            searchJob?.cancel()
            
            // Mulai pencarian baru dengan delay
            searchJob = MainScope().launch {
                delay(300) // Delay untuk menghindari terlalu banyak request
                searchRecipes(text.toString())
            }
        }
    }

    private fun setupObservers() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            progressBar.isVisible = false
            recipeAdapter.updateRecipes(recipes)
        }
    }

    private fun searchRecipes(query: String) {
        if (query.length >= 2) { // Minimal 2 karakter untuk mencari
            progressBar.isVisible = true
            viewModel.searchRecipes(query)
        } else {
            recipeAdapter.updateRecipes(emptyList())
        }
    }
} 