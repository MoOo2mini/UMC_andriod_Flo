package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePannelBackgroundVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    val fragmentList = listOf<Fragment>(PannelfirFragment(), PannelsecFragment(), PannelthrFragment())

    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}