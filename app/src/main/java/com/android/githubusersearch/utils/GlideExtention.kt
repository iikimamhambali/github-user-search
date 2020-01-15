package com.android.githubusersearch.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.loadFromResource(@DrawableRes imageRes: Int) {
    val options = RequestOptions()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
    Glide.with(this.context)
        .load(imageRes)
        .apply(options)
        .into(this)
}

fun ImageView.loadFromUrlWithPlaceholder(
    imageUrl: String?,
    @DrawableRes placeholder: Int,
    @DrawableRes errorPlaceholder: Int,
    onSuccessLoad: () -> Unit = {},
    onErrorLoad: () -> Unit = {}
) {
    val options = RequestOptions()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
    Glide.with(this.context)
        .load(imageUrl ?: "")
        .apply(options)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onErrorLoad.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccessLoad.invoke()
                return false
            }

        })
        .placeholder(placeholder)
        .error(errorPlaceholder)
        .into(this)
}