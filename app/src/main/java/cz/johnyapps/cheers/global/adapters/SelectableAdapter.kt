package cz.johnyapps.cheers.global.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import cz.johnyapps.cheers.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class SelectableAdapter<T, VH: SelectableAdapter<T, VH>.SelectableViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val lifecycleScope: LifecycleCoroutineScope
): ClickableAdapter<T, VH>(diffCallback, lifecycleScope) {
    private val _allowSelection = MutableStateFlow(true)
    val allowSelection: StateFlow<Boolean> = _allowSelection

    private val _selectedItems = MutableSharedFlow<List<T>>()
    val selectedItems: SharedFlow<List<T>> = _selectedItems

    private val selected = HashMap<Long, T>()

    init {
        lifecycleScope.launch {
            rootClick.collect {
                if (isSelecting()) {
                    addSelected(it)
                }
            }
        }

        lifecycleScope.launch {
            rootLongClick.collect {
                addSelected(it)
            }
        }

        lifecycleScope.launch {
            allowSelection.collect {
                if (!it) {
                    cancelSelection()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val selected = isSelected(item)

        if (selected) {
            holder.itemView.foreground = ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_item_background)
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

    private fun isSelected(item: T): Boolean {
        return selected[getItemId(item)] != null
    }

    fun isSelecting(): Boolean {
        return selected.isNotEmpty()
    }

    private fun addSelected(item: T) {
        if (isSelected(item)) {
            removeSelected(item)
        } else {
            val size = selected.size
            selected[getItemId(item)] = item

            if (size != selected.size) {
                lifecycleScope.launch { _selectedItems.emit(selected.values.toList()) }
                notifyItemChanged(getItemPosition(item))
            }
        }
    }

    private fun removeSelected(item: T) {
        val size = selected.size
        selected.remove(getItemId(item))

        if (size != selected.size) {
            lifecycleScope.launch { _selectedItems.emit(selected.values.toList()) }
            notifyItemChanged(getItemPosition(item))
        }
    }

    fun cancelSelection() {
        while (selected.isNotEmpty()) {
            removeSelected(selected.values.first())
        }
    }

    fun selectAll() {
        currentList.forEach {
            addSelected(it)
        }
    }

    fun setAllowSelection(allow: Boolean) {
        lifecycleScope.launch {
            _allowSelection.emit(allow)
        }
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
}