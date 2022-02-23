package com.example.android.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.photogallery.databinding.ItemImageBinding

private val itemsDiff = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}

class GalleryRecyclerAdapter :
    ListAdapter<String, GalleryRecyclerAdapter.ImageViewHolder>(itemsDiff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val context = LayoutInflater.from(parent.context)
        return ImageViewHolder(ItemImageBinding.inflate(context, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindFoodCategory(getItem(position))
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindFoodCategory(imageUriPath: String) {
            binding.imageItem.load(imageUriPath)
        }
    }
}