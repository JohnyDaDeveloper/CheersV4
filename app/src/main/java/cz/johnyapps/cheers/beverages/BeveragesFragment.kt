package cz.johnyapps.cheers.beverages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.johnyapps.cheers.databinding.FragmentBeveragesBinding
import cz.johnyapps.cheers.global.fragments.ScopeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BeveragesFragment: ScopeFragment() {
    private lateinit var binding: FragmentBeveragesBinding
    private lateinit var adapter: BeverageAdapter
    private val viewModel: BeveragesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeveragesBinding.inflate(inflater, container, false)
        adapter = BeverageAdapter(lifecycleScope)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.beveragesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.beveragesRecyclerView.adapter = adapter

        launchWhenStarted {
            adapter.rootClick.collect {
                navigateToBeverageFragment(it.id)
            }
        }

        launchWhenStarted {
            adapter.rootLongClick.collect {
                navigateToBeverageFragment(it.id)
            }
        }

        launchWhenStarted {
            viewModel.beverages.map {
                it.map { beverage -> BeverageListEntity(beverage) }
            }.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun navigateToBeverageFragment(beverageId: Long) {
        findNavController().navigate(
            BeveragesFragmentDirections.actionOpenBeverage(beverageId)
        )
    }
}