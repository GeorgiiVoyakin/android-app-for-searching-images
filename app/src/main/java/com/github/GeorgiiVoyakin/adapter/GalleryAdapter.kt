package com.github.GeorgiiVoyakin.adapter

import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.GeorgiiVoyakin.R

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    private var items: List<Uri> = emptyList()
    private var onItemClickListener: ((position: Int) -> Unit)? = null

    fun setItems(images: List<Uri>) {
        items = images
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = items[position]
        holder.bind(imageUri)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): Uri {
        return items[position]
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.row_image)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }
        }

        fun bind(imageUri: Uri) {
            val contentResolver = itemView.context.contentResolver
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            imageView.setImageBitmap(bitmap)
        }
    }
}
