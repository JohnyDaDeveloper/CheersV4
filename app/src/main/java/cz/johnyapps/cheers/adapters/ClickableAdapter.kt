package cz.johnyapps.cheers.adapters

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.johnyapps.cheers.utils.clicks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
abstract class ClickableAdapter<T, VH: ClickableAdapter<T, VH>.ClickableViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val lifecycleScope: LifecycleCoroutineScope
): ListAdapter<T, VH>(diffCallback) {
    private val _rootClickFlow = MutableStateFlow<T?>(null)
    val rootClickFlow: StateFlow<T?> = _rootClickFlow

    @ExperimentalCoroutinesApi
    open inner class ClickableViewHolder(root: View): RecyclerView.ViewHolder(root) {
        init {
            lifecycleScope.launchWhenCreated {
                root.clicks(lifecycleScope).collect {
                    _rootClickFlow.emit(getItem(adapterPosition))
                }
            }
        }
    }
}