package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class SavedSongRVAdapter() :
    RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId : Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song : Song)
        {
            binding.itemAlbumTitleTv.text = song.title
            binding.itemAlbumSingerTv.text = song.singer
            binding.itemSaveAlbumImgIv.setImageResource(song.coverImg!!)
        }
    }
}
//
//    fun addItem(save: Save){
//        saveList.add(save)
//        notifyDataSetChanged()
//    }
//
//    fun removeItem(position : Int){
//        saveList.removeAt(position)
//        notifyDataSetChanged()
//    }

//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveRVAdapter.ViewHolder {
//        val binding: ItemSaveBinding = ItemSaveBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: SaveRVAdapter.ViewHolder, position: Int) {
//        holder.bind(saveList[position])
//        //holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(saveList[position]) }
//        holder.binding.itemSaveMoreIv.setOnClickListener {mItemClickListener.onRemoveAlbum(position)}
//    }
//
//    override fun getItemCount(): Int = saveList.size

//    inner class ViewHolder(val binding: ItemSaveBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(save: Save)
//        {
//            binding.itemAlbumTitleTv.text = save.title
//            binding.itemAlbumSingerTv.text = save.singer
//            binding.itemSaveAlbumImgIv.setImageResource(save.coverImg!!)
//        }
//    }
//  }