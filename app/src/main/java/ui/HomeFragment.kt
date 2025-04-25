package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.example.aplikasiresepmasakan.R
import data.model.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val imageView = view.findViewById<ImageView>(R.id.imageMeal)
        val name = view.findViewById<TextView>(R.id.tvMealName)
        val category = view.findViewById<TextView>(R.id.tvMealCategory)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            name.text = meal.strMeal
            category.text = "${meal.strCategory} - ${meal.strArea}"
            imageView.load(meal.strMealThumb) {
                crossfade(true)
            }
        }

        swipe.setOnRefreshListener {
            viewModel.fetchRandomMeal()
            swipe.isRefreshing = false
        }
    }
}
