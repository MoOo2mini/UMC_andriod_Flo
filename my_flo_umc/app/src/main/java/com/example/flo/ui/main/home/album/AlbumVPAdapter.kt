package com.example.flo.ui.main.home.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.DetailFragment
import com.example.flo.VideoFragment
import com.example.flo.ui.song.SongFragment

class AlbumVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3
    // 수록곡, 상세정보, 영상 총 3개 --> 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}