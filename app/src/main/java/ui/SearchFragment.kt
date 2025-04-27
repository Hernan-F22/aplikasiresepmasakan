package ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiresepmasakan.R
import data.model.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSearchResults)

        adapter = MealAdapter() // adapter buatan kamu sendiri
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.searchResults.observe(viewLifecycleOwner) { meals ->
            adapter.setMeals(meals)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMeals(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}

