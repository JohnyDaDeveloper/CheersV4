package cz.johnyapps.cheers.itemwithid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import cz.johnyapps.cheers.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ItemsWithIdAdapter(
    private val list: List<ItemWithId>,
    private val scope: CoroutineScope
): BaseAdapter(), Filterable {
    private var filteredItems = list
    private val _selectedItem = MutableSharedFlow<ItemWithId?>()
    val selectedItem: SharedFlow<ItemWithId?> = _selectedItem

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): ItemWithId {
        return filteredItems[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).getItemId()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_with_id, parent, false)
        root.setOnClickListener {
            scope.launch {
                _selectedItem.emit(getItem(position))
            }
        }

        val textView: TextView = root.findViewById(R.id.textView)
        textView.text = getItem(position).getText()

        val divider: View = root.findViewById(R.id.divider)
        if (position == count - 1) {
            divider.visibility = View.GONE
        } else {
            divider.visibility = View.VISIBLE
        }

        return root
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint == null) {
                    val filterResults = FilterResults()
                    filterResults.values = list
                    filterResults.count = list.size

                    return filterResults
                }

                val filtered = ArrayList<ItemWithId>()
                val filterString = constraint.toString().trim().lowercase()

                list.forEach {
                    if (it.getText().trim().lowercase().contains(filterString)) {
                        filtered.add(it)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filtered
                filterResults.count = filtered.size

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = if (results != null) {
                    results.values as List<ItemWithId>
                } else {
                    list
                }
            }
        }
    }
}