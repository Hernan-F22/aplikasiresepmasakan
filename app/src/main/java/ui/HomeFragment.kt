package  ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aplikasiresepmasakan.R
import data.model.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Mencari RecyclerView dan SwipeRefreshLayout
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvMeals)  // Pastikan ID ini ada di XML
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        // Inisialisasi MealAdapter dan set ke RecyclerView
        mealAdapter = MealAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mealAdapter

        // Observasi data meals
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            if (meals.isNotEmpty()) {
                mealAdapter.setMeals(meals)  // Kirim list meal ke adapter
            }
        }

        // Refresh data saat swipe
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchMeals()  // Panggil fetchMeals untuk mendapatkan data
            swipeRefreshLayout.isRefreshing = false  // Set swipeRefreshLayout selesai refreshing
        }

        // Panggil fetchMeals pertama kali saat view pertama kali muncul
        viewModel.fetchMeals()
    }
}

