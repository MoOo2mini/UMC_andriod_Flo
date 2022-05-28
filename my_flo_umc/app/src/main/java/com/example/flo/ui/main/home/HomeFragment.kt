package com.example.flo.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.*
import com.example.flo.data.entities.Album
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.main.MainActivity
import com.example.flo.ui.main.home.album.AlbumFragment
import com.example.flo.ui.main.home.album.AlbumRVAdapter
import com.example.flo.ui.song.SongDatabase
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class
HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var currentPage = 0
    private val information = listOf<Fragment>(PannelfirFragment(), PannelsecFragment(), PannelthrFragment())
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll((songDB.albumDao().getAlbums()))

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album){
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })

        val pannelAdaper = HomePannelBackgroundVPAdapter(this)
        binding.homePannelBackgroundVp.adapter = pannelAdaper
        TabLayoutMediator(binding.dotsIndicatorTb, binding.homePannelBackgroundVp)
        { tab, position ->
            information[position]
        }.attach()

        val thread = Thread(PagerRunnable())
        thread.start()


        binding.wdotsIndicator.setViewPager2(binding.homePannelBackgroundVp)

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            }).commitAllowingStateLoss()
    }

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                try {
                    Thread.sleep(2000)
                    handler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    Log.d("interupt", "interupt발생")
                }
            }
        }
    }

    fun setPage() {
        if (currentPage == 3)
            currentPage = 0
        binding.homePannelBackgroundVp.setCurrentItem(currentPage, true)
        currentPage += 1
    }
}