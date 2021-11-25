package cz.johnyapps.cheers.global.dto

data class Counter(
    var id: Long, //TODO Change to val
    val beverage: Beverage,
    val volume: Float,
    val active: Boolean,
    val entries: MutableList<Entry>
) {
    constructor(
        beverage: Beverage,
        volume: Float
    ): this(
        0,
        beverage,
        volume,
        true,
        ArrayList<Entry>()
    )
}