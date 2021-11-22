package cz.johnyapps.cheers.dto

import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity

data class Category(
    val name: String,
    val icon: Icon
) {
    constructor(category: CategoryDbEntity): this(
        category.name,
        category.icon
    )
}