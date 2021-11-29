package cz.johnyapps.cheers.beverage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import cz.johnyapps.cheers.databinding.FragmentBeverageBinding
import cz.johnyapps.cheers.global.fragments.ScopeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BeverageFragment: ScopeFragment() {
    private lateinit var binding: FragmentBeverageBinding
    private val args: BeverageFragmentArgs by navArgs()
    private val viewModel: BeverageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeverageBinding.inflate(inflater, container, false)

        viewModel.loadBeverage(args.beverageId)

        setupBeverage()

        return binding.root
    }

    private fun setupBeverage() {
        launchWhenStarted {
            viewModel.beverage.collect {
                binding.beverage = it
            }
        }
    }
}