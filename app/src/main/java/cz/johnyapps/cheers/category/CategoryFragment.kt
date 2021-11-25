package cz.johnyapps.cheers.category

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.ScopeFragment
import cz.johnyapps.cheers.counter.CountersAdapter
import cz.johnyapps.cheers.counter.NewCounterDialog
import cz.johnyapps.cheers.databinding.FragmentCategoryBinding
import cz.johnyapps.cheers.global.dto.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class CategoryFragment(): ScopeFragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    private var category: Category? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val counterAdapter = CountersAdapter(lifecycleScope)

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
        setupBottomSheet()
        setupCounters()

        if (category != null) {
            viewModel.setCategory(category as Category)
        }

        return binding.root
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

    private fun setupCounters() {
        binding.countersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.countersRecyclerView.adapter = counterAdapter

        launchWhenStarted {
            viewModel.counters.collect {
                counterAdapter.submitList(it)
            }
        }

        launchWhenStarted {
            counterAdapter.counterHeight.collect {
                bottomSheetBehavior.peekHeight = it
            }
        }

        launchWhenStarted {
            counterAdapter.counterUpdate.debounce(500L).collect {
                viewModel.saveCounter(it)
            }
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

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
    }

    private fun setupCategory() {
        lifecycleScope.launchWhenStarted {
            viewModel.category.collect { category ->
                binding.category = category
            }
        }
    }
}