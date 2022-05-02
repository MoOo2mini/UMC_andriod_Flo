package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var currentPage = 0
    private val information = listOf<Fragment>(PannelfirFragment(), PannelsecFragment(), PannelthrFragment())
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        albumDatas.apply{
            add(Album("BUTTER", "BTS (방탄소년단)", R.drawable.img_album_exp))
            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파", R.drawable.img_album_exp3))
            add(Album("Boy in LUV", "BTS (방탄소년단)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
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