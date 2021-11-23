package cz.johnyapps.cheers.dto

data class Counter(
    var id: Int,
    val beverageId: Int,
    val volume: Float,
    val active: Boolean,
    val entries: MutableList<Entry>
) {
    constructor(
        beverageId: Int,
        volume: Float
    ): this(
        0,
        beverageId,
        volume,
        true,
        ArrayList<Entry>()
    )
}