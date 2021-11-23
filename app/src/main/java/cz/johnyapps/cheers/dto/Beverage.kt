package cz.johnyapps.cheers.dto

data class Beverage(
    var id: Long,
    val name: String,
    val color: Int,
    val textColor: Int,
    val description: String?
) {
    constructor(
        name: String,
        color: Int,
        textColor: Int,
    ): this(
        0,
        name,
        color,
        textColor,
        null
    )
}