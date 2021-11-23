package cz.johnyapps.cheers.dto

data class Counter(
    var id: Long, //TODO Change to val
    val beverage: Beverage,
    val alcohol: Float,
    val volume: Float,
    val active: Boolean,
    val entries: MutableList<Entry>
) {
    constructor(
        beverage: Beverage,
        alcohol: Float,
        volume: Float
    ): this(
        0,
        beverage,
        alcohol,
        volume,
        true,
        ArrayList<Entry>()
    )
}