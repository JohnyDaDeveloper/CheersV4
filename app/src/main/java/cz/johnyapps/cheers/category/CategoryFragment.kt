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
import cz.johnyapps.cheers.global.fragments.ScopeFragment
import cz.johnyapps.cheers.counter.CountersAdapter
import cz.johnyapps.cheers.counter.dialogs.DeleteCountersDialog
import cz.johnyapps.cheers.counter.dialogs.NewCounterDialog
import cz.johnyapps.cheers.counter.dialogs.StopCountersDialog
import cz.johnyapps.cheers.databinding.FragmentCategoryBinding
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.fragments.OnBackSupportFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class CategoryFragment(): ScopeFragment(), OnBackSupportFragment {
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
        if (viewModel.listSelectedCounter.value != null) {
            inflater.inflate(R.menu.counters_menu, menu)
        } else {
            inflater.inflate(R.menu.category_menu, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newCounterMenuItem -> {
                showNewCounterDialog()
                true
            }

            R.id.stopCounterMenuItem -> {
                val counters = viewModel.listSelectedCounter.value
                counters?.let { showStopCountersDialog(listOf(counters.toGlobalDto())) }
                true
            }

            R.id.deleteCounterMenuItem -> {
                val counters = viewModel.listSelectedCounter.value
                counters?.let { showDeleteCountersDialog(listOf(counters.toGlobalDto())) }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showStopCountersDialog(counters: List<Counter>) {
        val dialog = StopCountersDialog(counters)
        dialog.show(childFragmentManager, StopCountersDialog.TAG)

        lifecycleScope.launch {
            dialog.stop.collect { viewModel.stopCounters(it) }
        }
    }

    private fun showDeleteCountersDialog(counters: List<Counter>) {
        val dialog = DeleteCountersDialog(counters)
        dialog.show(childFragmentManager, DeleteCountersDialog.TAG)

        lifecycleScope.launch {
            dialog.delete.collect { viewModel.deleteCounters(it) }
        }
    }

    private fun setupCounters() {
        binding.countersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.countersRecyclerView.adapter = counterAdapter

        viewModel.collectSelectedCounters(counterAdapter.selectedItem.map { it.newItem })
        viewModel.collectCounterUpdate(counterAdapter.counterUpdate)

        launchWhenStarted {
            viewModel.counters.collect {
                counterAdapter.submitList(it)
            }
        }

        launchWhenStarted {
            counterAdapter.counterHeight.collect {
                bottomSheetBehavior.peekHeight = it
                val params = binding.categoryImageView.layoutParams as ViewGroup.MarginLayoutParams
                params.bottomMargin = it
                binding.categoryImageView.layoutParams = params
                binding.categoryImageView.requestLayout()
            }
        }

        launchWhenStarted {
            viewModel.listSelectedCounter.collect {
                requireActivity().invalidateOptionsMenu()
            }
        }
    }

    private fun showNewCounterDialog() {
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
        launchWhenStarted {
            viewModel.category.collect { category ->
                binding.category = category
            }
        }

        launchWhenStarted {
            viewModel.categorySelectedCounter.collect {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED ||
            bottomSheetBehavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            true
        } else {
            false
        }
    }
}