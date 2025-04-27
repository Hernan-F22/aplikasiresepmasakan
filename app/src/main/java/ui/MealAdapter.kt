package ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aplikasiresepmasakan.R
import data.model.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private var mealList = listOf<Meal>()

    // Method untuk update data list dan memberitahu RecyclerView untuk update tampilan
    fun setMeals(meals: List<Meal>) {
        mealList = meals
        notifyDataSetChanged()  // Memberitahu RecyclerView untuk mengupdate tampilan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.nameTextView.text = meal.strMeal
        holder.categoryTextView.text = meal.strCategory
        holder.imageView.load(meal.strMealThumb) {
            crossfade(true)
        }
    }

    override fun getItemCount(): Int = mealList.size  // Mengembalikan jumlah item di list

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageMeal)
        val nameTextView: TextView = itemView.findViewById(R.id.tvMealName)
        val categoryTextView: TextView = itemView.findViewById(R.id.tvMealCategory)
    }
}



