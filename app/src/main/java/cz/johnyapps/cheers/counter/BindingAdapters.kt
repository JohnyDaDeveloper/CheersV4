package cz.johnyapps.cheers.counter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import cz.johnyapps.cheers.dto.Beverage

object BindingAdapters {
    @BindingAdapter("app:alcohol")
    @JvmStatic
    fun appAlcohol(editText: EditText, beverage: Beverage?) {
        if (beverage?.alcohol ?: 0F != 0F) {
            editText.setText(beverage?.alcohol.toString())
        }
    }

    @BindingAdapter("app:beverage")
    @JvmStatic
    fun appBeverage(editText: EditText, beverage: Beverage?) {
        if (beverage != null) {
            editText.setText(beverage.name)
            editText.setSelection(editText.text.length)
        }
    }
}