package cz.johnyapps.cheers.utils

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorInt
import androidx.annotation.AttrRes
import android.graphics.Color
import java.util.*
import kotlin.math.roundToInt

object ThemeUtils {
    @ColorInt
    fun getAttributeColor(@AttrRes attr: Int, context: Context): Int {
        val attrs = intArrayOf(attr)
        val color: Int
        val typedArray = context.obtainStyledAttributes(attrs)
        color = try {
            typedArray.getColor(0, Color.BLACK)
        } finally {
            typedArray.recycle()
        }
        return color
    }

    @ColorInt
    fun getNextColor(@ColorInt color: Int): Int {
        val red = (Color.red(color) * 1.3f % 125).roundToInt() + 130
        val green = (Color.green(color) * 1.5f % 125).roundToInt() + 130
        val blue = (Color.blue(color) * 1.7f % 125).roundToInt() + 130
        return Color.rgb(red, green, blue)
    }

    @ColorInt
    fun getRandomColor(): Int {
        val random = Random()
        val order = intArrayOf(-1, -1, -1)
        var pos = random.nextInt(3)
        order[pos] = 2
        pos = (pos + 1) % 3
        var `val` = random.nextInt(2)
        order[pos] = `val`
        pos = (pos + 1) % 3
        `val` = (`val` + 1) % 2
        order[pos] = `val`
        val colors = IntArray(3)
        for (i in colors.indices) {
            colors[i] = random.nextInt(255)
        }
        var color = Color.rgb(colors[order[0]], colors[order[1]], colors[order[2]])
        var lum = Color.luminance(color)
        while (lum < 0.5) {
            pos = random.nextInt(3)
            colors[pos] = (colors[pos] * 1.3f).toInt().coerceAtMost(255)
            color = Color.rgb(colors[order[0]], colors[order[1]], colors[order[2]])
            lum = Color.luminance(color)
        }
        return color
    }

    @ColorInt
    fun addAlpha(alpha: Int, color: Int): Int {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

    fun isSystemInNightMode(context: Context): Boolean {
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).roundToInt()
    }
}