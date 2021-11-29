package cz.johnyapps.cheers.beverages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import cz.johnyapps.cheers.databinding.ItemBeverageBinding
import cz.johnyapps.cheers.global.adapters.ClickableAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BeverageAdapter(scope: LifecycleCoroutineScope): ClickableAdapter<BeverageListEntity, BeverageAdapter.BeverageViewHolder>(
    DIFF_CALLBACK,
    scope
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeverageViewHolder {
        return BeverageViewHolder(
            ItemBeverageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BeverageViewHolder, position: Int) {
        holder.binding.beverage = getItem(position)
    }

    open inner class BeverageViewHolder(
        val binding: ItemBeverageBinding
    ): ClickableAdapter<BeverageListEntity, BeverageViewHolder>.ClickableViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BeverageListEntity>() {
            override fun areItemsTheSame(
                oldItem: BeverageListEntity,
                newItem: BeverageListEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BeverageListEntity,
                newItem: BeverageListEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}