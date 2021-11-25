package cz.johnyapps.cheers.global.enums

import androidx.annotation.DrawableRes
import cz.johnyapps.cheers.R

enum class Icon(
    @DrawableRes val iconId: Int
) {
    BEER(R.drawable.ic_beer),
    WINE(R.drawable.ic_wine),
    SHOT(R.drawable.ic_shot)
}