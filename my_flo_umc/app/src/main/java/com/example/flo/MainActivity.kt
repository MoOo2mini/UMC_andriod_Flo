package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var song : Song
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initSong()
        //setPlayer(song)
        initBottomNavigation()


        val song = Song(binding.mainMiniPlayerTitleTv.text.toString(), binding.mainMiniPlayerSingerTv.text.toString(), 0, 60 , false, "music_lilac")

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }

        binding.mainMiniplayerBtn.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.mainMiniplayerPauseBtn.setOnClickListener{
            setPlayerStatus(false)
        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.mainMiniPlayerTitleTv.text = intent.getStringExtra("title")
            binding.mainMiniPlayerSingerTv.text = intent.getStringExtra("singer")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    fun setPlayerStatus(isPlaying : Boolean){
//        song.isPlaying = isPlaying
//        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainMiniplayerPauseBtn.visibility = View.VISIBLE
        }
        else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainMiniplayerPauseBtn.visibility = View.GONE
        }
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.mainMiniPlayerTitleTv.text = intent.getStringExtra("title")!!
        binding.mainMiniPlayerSingerTv.text = intent.getStringExtra("singer")!!
        binding.miniplayerSongProgressSb.progress = (song.second * 1000 / song.playTime)
    }


    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime)
                    {
                        break
                    }
                    if (isPlaying)
                    {
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.miniplayerSongProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }

                        // mills 천 단위마다 1초가 지남.
                        if (mills % 1000 == 0f){
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }

    }


    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}