package com.example.android.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.photogallery.databinding.ItemImageBinding

class GalleryRecyclerAdapter :
    RecyclerView.Adapter<GalleryRecyclerAdapter.ImageViewHolder>() {

    private var gallery: ArrayList<String> = ArrayList()
    private lateinit var binding: ItemImageBinding

    fun setGallery(data: ArrayList<String>) {
        this.gallery = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = gallery[position]

        holder.bindFoodCategory(image)

    }

    override fun getItemCount() = gallery.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindFoodCategory(image: String) {
            binding.imageView.load(image)
        }
    }
}