package com.example.aplikasiresepmasakan.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiresepmasakan.R
import com.example.aplikasiresepmasakan.adapter.RecipeAdapter
import com.example.aplikasiresepmasakan.viewmodel.RecipeViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var searchEditText: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var emptyStateLayout: View
    private lateinit var recipeAdapter: RecipeAdapter
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

        // Inisialisasi views
        searchEditText = view.findViewById(R.id.searchEditText)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout)

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
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    private fun setupSearch() {
        // Pencarian saat mengetik
        searchEditText.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(300) // Delay untuk menghindari terlalu banyak request
                text?.toString()?.let { query ->
                    performSearch(query)
                }
            }
        }

        // Pencarian saat menekan tombol search di keyboard
        searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                searchEditText.text?.toString()?.let { query ->
                    performSearch(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupObservers() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            hideLoading()
            if (recipes.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
                recipeAdapter.updateRecipes(recipes)
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.length >= 2) { // Minimal 2 karakter untuk mencari
            showLoading()
            viewModel.searchRecipes(query)
        } else {
            recipeAdapter.updateRecipes(emptyList())
            if (query.isEmpty()) {
                hideEmptyState()
            } else {
                showEmptyState()
            }
        }
    }

    private fun showLoading() {
        progressBar.isVisible = true
        recyclerView.isVisible = false
        emptyStateLayout.isVisible = false
    }

    private fun hideLoading() {
        progressBar.isVisible = false
        recyclerView.isVisible = true
    }

    private fun showEmptyState() {
        emptyStateLayout.isVisible = true
        recyclerView.isVisible = false
    }

    private fun hideEmptyState() {
        emptyStateLayout.isVisible = false
        recyclerView.isVisible = true
    }
} 