package cz.johnyapps.cheers.global.utils

import java.text.DecimalFormat

object TextUtils {
    fun decimalToStringWithTwoDecimalDigits(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value)
    }
}