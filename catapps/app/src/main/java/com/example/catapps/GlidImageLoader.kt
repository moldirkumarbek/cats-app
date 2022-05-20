package com.example.catapps

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlidImageLoader(private val context: Context):ImageLoader {
    override fun loadImage(imageUrl: String, imageView:ImageView){
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }

}