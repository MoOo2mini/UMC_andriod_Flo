package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerPlayIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        binding.songRepeatOffIv.setOnClickListener{
            setRepeatStatus(true)
        }

        binding.songRepeatOnIv.setOnClickListener{
            setRepeatStatus(false)
        }

        binding.songRandomOffIv.setOnClickListener{
            setRandomStatus(true)
        }

        binding.songRandomOnIv.setOnClickListener{
            setRandomStatus(false)
        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }



    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerPlayIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerPlayIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setRandomStatus(isRandom : Boolean){
        if(isRandom){
            binding.songRandomOnIv.visibility = View.VISIBLE
            binding.songRandomOffIv.visibility = View.GONE
        }
        else {
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomOffIv.visibility = View.VISIBLE
        }
    }

    fun setRepeatStatus(isRepeat : Boolean){
        if(isRepeat){
            binding.songRepeatOnIv.visibility = View.VISIBLE
            binding.songRepeatOffIv.visibility = View.GONE
        }
        else {
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatOffIv.visibility = View.VISIBLE
        }
    }
}