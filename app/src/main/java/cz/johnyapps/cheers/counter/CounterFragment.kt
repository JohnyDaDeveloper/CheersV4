package cz.johnyapps.cheers.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.FragmentCounterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect

@FlowPreview
@AndroidEntryPoint
class CounterFragment: Fragment() {
    private lateinit var binding: FragmentCounterBinding
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counter, container, false)
        binding.plusButton.setOnClickListener {
            viewModel.increaseCount()
        }
        binding.minusButton.setOnClickListener {
            viewModel.decreaseCount()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.count.collect {
                binding.valueTextView.text = it.size.toString()
            }
        }

        return binding.root
    }
}