package cz.johnyapps.cheers.category

import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.Sound
import kotlinx.coroutines.flow.MutableStateFlow

data class CategoryEntity(
    val id: Long,
    val name: String,
    val icon: Icon,
    val sounds: List<Sound>,
    val selectedCounter: MutableStateFlow<Counter?>,
    val order: Int
) {
    constructor(category: Category): this(
        category.id,
        category.name,
        category.icon,
        category.sounds,
        MutableStateFlow(category.selectedCounter),
        category.order
    )

    fun toGlobalDto(): Category {
        return Category(
            id,
            name,
            icon,
            sounds,
            selectedCounter.value,
            order
        )
    }
}