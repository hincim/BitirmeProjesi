package com.example.muhendisliktasarimi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.domain.model.Score


fun String.capitalizeFirstLetter(): String {
    return if (isNotEmpty()) {
        this[0].toUpperCase() + substring(1)
    } else {
        this
    }
}


@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?){

    view.getImageMy(url, placeHolderProgressBar(view.context))
}
fun ImageView.getImageMy(url: String?, progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.app_logo)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
fun placeHolderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
