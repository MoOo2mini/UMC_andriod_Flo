package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSaveBinding

class SaveRVAdapter(private val saveList: ArrayList<Save>) :
    RecyclerView.Adapter<SaveRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(save : Save)
        fun onRemoveAlbum(position: Int)
    }
    private lateinit var mItemClickListener: SaveRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener: SaveRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    } // homefrgment에서 리스너 객체를 던져준다.

    fun addItem(save: Save){
        saveList.add(save)
        notifyDataSetChanged()
    }

    fun removeItem(position : Int){
        saveList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SaveRVAdapter.ViewHolder {
        val binding: ItemSaveBinding = ItemSaveBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveRVAdapter.ViewHolder, position: Int) {
        holder.bind(saveList[position])
        //holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(saveList[position]) }
        holder.binding.itemSaveMoreIv.setOnClickListener {mItemClickListener.onRemoveAlbum(position)}
    }

    override fun getItemCount(): Int = saveList.size

    inner class ViewHolder(val binding: ItemSaveBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(save: Save)
        {
            binding.itemAlbumTitleTv.text = save.title
            binding.itemAlbumSingerTv.text = save.singer
            binding.itemSaveAlbumImgIv.setImageResource(save.coverImg!!)
        }
    }

}
