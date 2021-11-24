package cz.johnyapps.cheers

import androidx.annotation.DrawableRes

enum class Icon(
    @DrawableRes val iconId: Int
) {
    BEER(R.drawable.ic_beer),
    WINE(R.drawable.ic_wine),
    SHOT(R.drawable.ic_shot)
}