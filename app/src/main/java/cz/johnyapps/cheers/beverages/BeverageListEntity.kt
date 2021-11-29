package cz.johnyapps.cheers.beverages

import cz.johnyapps.cheers.global.dto.Beverage

data class BeverageListEntity(
    var id: Long,
    val name: String,
    val alcohol: Float,
    val description: String?
) {
    constructor(beverage: Beverage): this(
        beverage.id,
        beverage.name,
        beverage.alcohol,
        beverage.description
    )
}