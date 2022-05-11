package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSaveBinding
import com.google.gson.Gson

class SaveFragment : Fragment(){
    lateinit var binding: FragmentSaveBinding
    private var albumDatas = ArrayList<Save>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(inflater, container, false)

        albumDatas.apply{
            add(Save("BUTTER", "BTS (방탄소년단)", R.drawable.img_album_exp))
            add(Save("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Save("Next Level", "에스파", R.drawable.img_album_exp3))
            add(Save("Boy in LUV", "BTS (방탄소년단)", R.drawable.img_album_exp4))
            add(Save("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Save("Weekend", "태연", R.drawable.img_album_exp6))
            add(Save("BUTTER", "BTS (방탄소년단)", R.drawable.img_album_exp))
            add(Save("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Save("Next Level", "에스파", R.drawable.img_album_exp3))
            add(Save("Boy in LUV", "BTS (방탄소년단)", R.drawable.img_album_exp4))
            add(Save("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Save("Weekend", "태연", R.drawable.img_album_exp6))
            add(Save("BUTTER", "BTS (방탄소년단)", R.drawable.img_album_exp))
            add(Save("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Save("Next Level", "에스파", R.drawable.img_album_exp3))
            add(Save("Boy in LUV", "BTS (방탄소년단)", R.drawable.img_album_exp4))
            add(Save("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Save("Weekend", "태연", R.drawable.img_album_exp6))
        }

        val saveRVAdapter = SaveRVAdapter(albumDatas)
        binding.lockerSaveContentRv.adapter = saveRVAdapter
        binding.lockerSaveContentRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        saveRVAdapter.setMyItemClickListener(object : SaveRVAdapter.MyItemClickListener{
            override fun onItemClick(save : Save) {
                changeAlbumFragment(save)
            }

            override fun onRemoveAlbum(position: Int) {
                saveRVAdapter.removeItem(position)
            }
        })

        return binding.root
    }

    private fun changeAlbumFragment(save: Save) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(save)
                    putString("album", albumJson)
                }
            }).commitAllowingStateLoss()
    }
}