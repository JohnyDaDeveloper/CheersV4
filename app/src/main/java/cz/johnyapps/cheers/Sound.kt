package cz.johnyapps.cheers

import androidx.annotation.RawRes

enum class Sound(
    @RawRes val soundId: Int
) {
    BEER1(R.raw.beer1),
    BEER2(R.raw.beer2),
    WINE1(R.raw.wine1),
    WINE2(R.raw.wine2)
}