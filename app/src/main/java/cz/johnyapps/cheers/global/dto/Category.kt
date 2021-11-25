package cz.johnyapps.cheers.global.dto

import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.Sound

data class Category(
    val id: Long,
    val name: String,
    val icon: Icon,
    val sounds: List<Sound>,
    val selectedCounter: Counter?,
    val order: Int
): Comparable<Category> {
    override fun compareTo(other: Category): Int {
        if (other.order < order) {
            return 1
        } else if (other.order == order) {
            return 0
        }

        return -1
    }
}