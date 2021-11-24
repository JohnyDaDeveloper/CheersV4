package cz.johnyapps.cheers.dto

import cz.johnyapps.cheers.itemwithid.ItemWithId

data class Beverage(
    var id: Long,
    val name: String,
    val alcohol: Float,
    val color: Int,
    val textColor: Int,
    val description: String?
): ItemWithId {
    constructor(
        name: String,
        alcohol: Float,
        color: Int,
        textColor: Int,
    ): this(
        0,
        name,
        alcohol,
        color,
        textColor,
        null
    )

    override fun getItemId(): Long {
        return id
    }

    override fun getText(): String {
        return name
    }

    fun hasThisNameIgnoreCase(name: String?): Boolean {
        return if (name == null) {
            false
        } else {
            name.trim().lowercase() == this.name.trim().lowercase()
        }
    }
}