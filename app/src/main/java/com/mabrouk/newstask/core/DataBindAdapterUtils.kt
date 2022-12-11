package com.mabrouk.core.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.mabrouk.newstask.R

class DataBindAdapterUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("app:loadImage")
        fun loadImages(view: ImageView, url: String?) {
                Glide.with(view)
                    .load(url?:"")
                    .placeholder(R.drawable.placeholder)
                    .into(view)
        }

        @JvmStatic
        @BindingAdapter("app:loadImage")
        fun loadImages(view: ShapeableImageView, url: String?) {
            url?.apply {
                Glide.with(view)
                    .load(url)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("app:loadImageResource")
        fun loadImage(view: ImageView, resource: Int) {
            view.setImageResource(resource)
        }

    }
}