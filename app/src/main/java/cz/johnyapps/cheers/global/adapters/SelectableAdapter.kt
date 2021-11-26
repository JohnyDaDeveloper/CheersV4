package cz.johnyapps.cheers.global.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import cz.johnyapps.cheers.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class SelectableAdapter<T, VH: SelectableAdapter<T, VH>.SelectableViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val lifecycleScope: LifecycleCoroutineScope
): ClickableAdapter<T, VH>(diffCallback, lifecycleScope) {
    private var _selectedItem = MutableStateFlow(OldAndNew<T>(null, null))
    val selectedItem: StateFlow<OldAndNew<T>> = _selectedItem

    init {
        lifecycleScope.launchWhenCreated {
            _selectedItem.collect {
                updateSelection(it.oldItem, it.newItem)
            }
        }

        lifecycleScope.launchWhenCreated {
            rootClick.collect {
                if (isSelecting()) {
                    selectNewItem(it)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            rootLongClick.collect {
                selectNewItem(it)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val selectedItem = this._selectedItem.value.newItem
        var selected = false

        if (selectedItem != null && getItemId(selectedItem) == getItemId(item)) {
            holder.itemView.foreground = ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_item_background)
            selected = true
        } else {
            holder.itemView.foreground = null
        }

        onBindViewHolder(holder, position, selected)
    }

    override fun submitList(list: List<T>?) {
        this.submitList(list, null)
    }

    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        cancelSelection()
        super.submitList(list, commitCallback)
    }

    private suspend fun selectNewItem(item: T?) {
        _selectedItem.emit(OldAndNew(_selectedItem.value.newItem, item))
    }

    private fun isSelecting(): Boolean {
        return _selectedItem.value.newItem != null
    }

    fun select(item: T?) {
        lifecycleScope.launch {
            selectNewItem(item)
        }
    }

    fun cancelSelection() {
        lifecycleScope.launch {
            selectNewItem(null)
        }
    }

    private fun updateSelection(oldItem: T?, newItem: T?) {
        if (newItem == oldItem) {
            cancelSelection()
            return
        }

        oldItem?.let { notifyItemChanged(getItemPosition(oldItem)) }
        newItem?.let { notifyItemChanged(getItemPosition(newItem)) }
    }

    private fun getItemPosition(item: T): Int {
        for (i in 0..itemCount) {
            if (getItemId(getItem(i)) == getItemId(item)) {
                return i
            }
        }

        return -1
    }

    abstract fun onBindViewHolder(holder: VH, position: Int, selected: Boolean)
    abstract fun getItemId(item: T): Long

    @ExperimentalCoroutinesApi
    open inner class SelectableViewHolder(root: View): ClickableAdapter<T, VH>.ClickableViewHolder(root)

    data class OldAndNew<T>(val oldItem: T?, val newItem: T?)
}