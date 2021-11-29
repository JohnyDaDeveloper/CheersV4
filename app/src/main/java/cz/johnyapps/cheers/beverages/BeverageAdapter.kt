package cz.johnyapps.cheers.beverages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.johnyapps.cheers.databinding.ItemBeverageBinding

class BeverageAdapter: ListAdapter<BeverageListEntity, BeverageAdapter.BeverageViewHolder>(
    DIFF_CALLBACK
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

    class BeverageViewHolder(val binding: ItemBeverageBinding): RecyclerView.ViewHolder(binding.root)

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