package cz.johnyapps.cheers.category

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.counter.CounterFragment
import cz.johnyapps.cheers.counter.NewCounterDialog
import cz.johnyapps.cheers.databinding.FragmentCategoryBinding
import cz.johnyapps.cheers.dto.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FlowPreview
class CategoryFragment(): Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    private var category: Category? = null

    constructor(category: Category): this() {
        this.category = category
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)


        setupCategory()
        setupCounter()

        if (category != null) {
            viewModel.setCategory(category as Category)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = binding.counterFragment.getFragment<CounterFragment>()
        fragment.setCounter(viewModel.counter)
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.category_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.newCounterMenuItem) {
            createNewCounter()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun createNewCounter() {
        val beverages = viewModel.beverages.value

        if (beverages != null) {
            val dialog = NewCounterDialog(beverages)
            dialog.show(childFragmentManager, NewCounterDialog.TAG)

            lifecycleScope.launch {
                dialog.newCounter.collect {
                    viewModel.saveCounter(it)
                }
            }
        } else {
            //TODO Handle beverages not yet loaded state
        }
    }

    private fun setupCounter() {
        lifecycleScope.launchWhenCreated {
            viewModel.counter.collect {
                val fragment = binding.counterFragment.getFragment<CounterFragment?>()
                fragment?.setCounter(viewModel.counter)
            }
        }
    }

    private fun setupCategory() {
        lifecycleScope.launchWhenStarted {
            viewModel.category.collect { category ->
                binding.category = category
                binding.counterFragment.visibility = if (category?.selectedCounter?.value == null) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
    }
}