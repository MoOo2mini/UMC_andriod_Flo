package com.example.flo

import java.util.ArrayList

data class Save (
    var title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
    var songs : ArrayList<Song>? = null
)