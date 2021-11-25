package cz.johnyapps.cheers.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.FragmentCategoriesBinding
import cz.johnyapps.cheers.global.fragments.OnBackSupportFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class CategoriesFragment: Fragment(), OnBackSupportFragment {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoryFragmentAdapter
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = CategoryFragmentAdapter(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onBackPressed(): Boolean {
        val fragment = adapter.getFragment(binding.viewPager.currentItem)
        return fragment is OnBackSupportFragment && fragment.onBackPressed()
    }
}