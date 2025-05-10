package com.example.aplikasiresepmasakan.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasiresepmasakan.ui.fragments.AboutFragment
import com.example.aplikasiresepmasakan.ui.fragments.HomeFragment
import com.example.aplikasiresepmasakan.ui.fragments.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> AboutFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
} 