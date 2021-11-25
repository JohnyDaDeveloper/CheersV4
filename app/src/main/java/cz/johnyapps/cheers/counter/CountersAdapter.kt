package cz.johnyapps.cheers.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.ItemCounterBinding
import cz.johnyapps.cheers.global.adapters.SelectableAdapter
import cz.johnyapps.cheers.global.dto.Counter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class CountersAdapter(
    private val lifecycleScope: LifecycleCoroutineScope
): SelectableAdapter<Counter, CountersAdapter.CountersViewHolder>(DIFF_CALL, lifecycleScope) {
    private val _counterUpdate = MutableSharedFlow<Counter>()
    val counterUpdate: SharedFlow<Counter> = _counterUpdate

    private val _counterHeight = MutableStateFlow(0)
    val counterHeight: StateFlow<Int> = _counterHeight

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: CountersViewHolder, position: Int, selected: Boolean) {
        lifecycleScope.launch {
            holder.binding.counterView.setCounter(getItem(position))

            holder.binding.counterView.counterUpdate.collect {
                _counterUpdate.emit(it)
            }
        }

        lifecycleScope.launch {
            holder.binding.counterView.height
                .filter { it > 0 }
                .collect {
                    _counterHeight.emit(it)
                }
        }
    }

    override fun getItemId(item: Counter): Long {
        return item.id
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountersViewHolder {
        return CountersViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_counter,
            parent,
            false
        ))
    }

    open inner class CountersViewHolder(
        val binding: ItemCounterBinding
    ): SelectableAdapter<Counter, CountersViewHolder>.SelectableViewHolder(binding.root)

    companion object {
        private val DIFF_CALL = object : DiffUtil.ItemCallback<Counter>() {
            override fun areItemsTheSame(oldItem: Counter, newItem: Counter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Counter, newItem: Counter): Boolean {
                return oldItem == newItem
            }
        }
    }
}