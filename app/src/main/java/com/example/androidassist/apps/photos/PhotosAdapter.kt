package com.example.androidassist.apps.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidassist.R
import java.io.File

class PhotosAdapter(private val photosList: ArrayList<String>, private val onPhotoClicked: (String) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageFile = File(photosList[position])
        if (imageFile.exists()) {
            Glide.with(holder.itemView.context).load(imageFile).into(holder.image)
        }

        holder.image.setOnClickListener {
            onPhotoClicked(photosList[position])
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView

        init {
            image = itemView.findViewById(R.id.gallery)
        }
    }
}
