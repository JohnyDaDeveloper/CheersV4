package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.Embedded
import androidx.room.Relation
import cz.johnyapps.cheers.global.dto.Category

data class CategoryAndCounterDbEntity(
    @Embedded
    val counter: CounterDbEntity,
    @Relation(
        parentColumn = "counter_id",
        entityColumn = "selected_counter_id"
    )
    val category: CategoryDbEntity?,
    @Relation(
        parentColumn = "beverage_id",
        entityColumn = "beverage_id"
    )
    val beverage: BeverageDbEntity
) {
    fun toGlobalDto(): Category? {
        return if (category == null) {
            null
        } else {
            Category(
                category.id,
                category.name,
                category.icon,
                category.sounds,
                counter.toGlobalDto(beverage.toGlobalDto()),
                category.order
            )
        }
    }
}