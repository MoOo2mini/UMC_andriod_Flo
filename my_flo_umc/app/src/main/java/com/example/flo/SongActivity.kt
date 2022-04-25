package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerPlayIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
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

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerPlayIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songMiniplayerPlayIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) { // 재생 중이 아닌데 정지를 시키면 에러가 나기 때문에 if문으로 검사한다.
                mediaPlayer?.pause()
            }
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

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    // 코틀린에서는 이너 클래스를 명시해주어야 한다.
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
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }

                        // mills 천 단위마다 1초가 지남.
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)

                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }

    }
}