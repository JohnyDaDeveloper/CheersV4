package cz.johnyapps.cheers.category

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("app:srcId")
    @JvmStatic
    fun appSrcId(imageView: ImageView, @DrawableRes resId: Int) {
        imageView.setImageResource(resId)
    }
}