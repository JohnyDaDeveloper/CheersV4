package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.Embedded
import androidx.room.Relation
import cz.johnyapps.cheers.global.dto.Category

data class CategoryAndCounterDbEntity(
    @Embedded
    val category: CategoryDbEntity,
    @Relation(
        parentColumn = "selected_counter_id",
        entityColumn = "counter_id",
        entity = CounterDbEntity::class
    )
    val counter: CounterAndBeverageDbEntity?
) {
    fun toGlobalDto(): Category = Category(
        category.id,
        category.name,
        category.icon,
        category.sounds,
        counter?.toGlobalDto(),
        category.order
    )
}