package com.example.flo.ui.main.look

import com.example.flo.ui.song.FloChartResult

interface LookView {
    fun onGetSongLoading()
    fun onGetSongSuccess(code : Int, result : FloChartResult)
    fun onGetSongFailure(code : Int, message : String)
}