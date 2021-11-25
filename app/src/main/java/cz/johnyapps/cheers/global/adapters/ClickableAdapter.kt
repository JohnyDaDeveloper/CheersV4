package cz.johnyapps.cheers.global.adapters

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.johnyapps.cheers.global.utils.clicks
import cz.johnyapps.cheers.global.utils.longClicks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
abstract class ClickableAdapter<T, VH: ClickableAdapter<T, VH>.ClickableViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val lifecycleScope: LifecycleCoroutineScope
): ListAdapter<T, VH>(diffCallback) {
    private val _rootClick = MutableSharedFlow<T>()
    val rootClick: SharedFlow<T> = _rootClick

    private val _rootLongClick = MutableSharedFlow<T>()
    val rootLongClick: SharedFlow<T> = _rootLongClick

    @ExperimentalCoroutinesApi
    open inner class ClickableViewHolder(root: View): RecyclerView.ViewHolder(root) {
        init {
            lifecycleScope.launchWhenCreated {
                root.clicks(lifecycleScope).collect {
                    _rootClick.emit(getItem(adapterPosition))
                }
            }

            lifecycleScope.launchWhenCreated {
                root.longClicks(lifecycleScope).collect {
                    _rootLongClick.emit(getItem(adapterPosition))
                }
            }
        }
    }
}