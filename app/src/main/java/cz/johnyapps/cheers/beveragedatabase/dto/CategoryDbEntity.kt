package cz.johnyapps.cheers.beveragedatabase.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.dto.Category

@Entity(tableName = "category_table")
data class CategoryDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Icon,
    @ColumnInfo(name = "selected_counter_id")
    val selectedCounterId: Int,
    @ColumnInfo(name = "order")
    val order: Int
) {
    fun toGlobalDto(): Category {
        return Category(
            id,
            name,
            icon,
            null,
            order
        )
    }
}