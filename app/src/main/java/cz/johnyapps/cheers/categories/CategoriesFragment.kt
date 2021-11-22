package cz.johnyapps.cheers.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.johnyapps.cheers.Icon
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.FragmentCategoriesBinding

class CategoriesFragment: Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoryFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = CategoryFragmentAdapter(this, listOf(
            Category("Beer", Icon.BEER),
            Category("Wine", Icon.WINE)
        ))

        binding.viewPager.adapter = adapter

        return binding.root
    }
}