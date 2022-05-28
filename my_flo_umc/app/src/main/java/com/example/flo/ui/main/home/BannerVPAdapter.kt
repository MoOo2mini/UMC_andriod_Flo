package com.example.flo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) :FragmentStateAdapter(fragment) {
    private val fragmentlist: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragmentlist.size
    // 데이터의 개수를 알려주는 함수

    override fun createFragment(position: Int): Fragment = fragmentlist[position]
    // 위의 함수에서 개수가 4개라고 카운트 되었다면 0 ,1, 2, 3이 실행된다.

    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment) //fragmentlist의 인자로 받은 fragment들을 추가해줄 것이다.
        notifyItemInserted(fragmentlist.size - 1)
    }
}