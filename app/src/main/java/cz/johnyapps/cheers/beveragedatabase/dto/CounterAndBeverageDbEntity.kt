package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.Embedded
import androidx.room.Relation
import cz.johnyapps.cheers.global.dto.Counter

data class CounterAndBeverageDbEntity(
    @Embedded
    val counter: CounterDbEntity,
    @Relation(
        parentColumn = "beverage_id",
        entityColumn = "beverage_id"
    )
    val beverage: BeverageDbEntity
) {
    fun toGlobalDto(): Counter {
        return Counter(
            counter.id,
            beverage.toGlobalDto(),
            counter.volume,
            counter.active,
            counter.entries
        )
    }
}