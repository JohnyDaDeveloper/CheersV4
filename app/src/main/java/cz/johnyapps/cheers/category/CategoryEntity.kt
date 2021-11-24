package cz.johnyapps.cheers.category

import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import kotlinx.coroutines.flow.MutableStateFlow

data class CategoryEntity(
    val id: Long,
    val name: String,
    val icon: Icon,
    val selectedCounter: MutableStateFlow<Counter?>,
    val order: Int
) {
    constructor(category: Category): this(
        category.id,
        category.name,
        category.icon,
        MutableStateFlow(category.selectedCounter),
        category.order
    )

    fun toGlobalDto(): Category {
        return Category(
            id,
            name,
            icon,
            selectedCounter.value,
            order
        )
    }
}