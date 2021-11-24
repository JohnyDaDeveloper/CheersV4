package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.Sound

@Entity(tableName = "category_table")
data class CategoryDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Icon,
    @ColumnInfo(name = "sounds")
    val sounds: List<Sound>,
    @ColumnInfo(name = "selected_counter_id")
    val selectedCounterId: Long,
    @ColumnInfo(name = "order")
    val order: Int
) {
    constructor(category: Category): this(
        category.id,
        category.name,
        category.icon,
        category.sounds,
        category.selectedCounter?.id ?: 0L,
        category.order
    )

    fun toGlobalDto(): Category {
        return Category(
            id,
            name,
            icon,
            sounds,
            null,
            order
        )
    }
}