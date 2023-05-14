package com.msayeh.asteroid.utlis

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.msayeh.asteroid.R
import com.msayeh.asteroid.domain.ImageOfTheDay
import com.msayeh.asteroid.main.Status
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("bindImage")
fun ImageView.bindWithPicasso(imageOfTheDay: ImageOfTheDay?) {
    imageOfTheDay ?: return
    Picasso.with(this.context)
        .load(imageOfTheDay.url)
        .into(this)

    contentDescription = imageOfTheDay.title
}

@BindingAdapter("statusVisibility")
fun View.statusVisibility(status: Status) {
    visibility = if (status == Status.LOADING) View.VISIBLE else View.GONE
}

@BindingAdapter("messageVisibility")
fun View.messageVisibility(status: Status?) {
    visibility = if(status == Status.ERROR) View.VISIBLE else View.GONE
}