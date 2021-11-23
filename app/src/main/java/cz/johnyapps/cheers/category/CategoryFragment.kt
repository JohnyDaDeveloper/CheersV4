package cz.johnyapps.cheers.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.databinding.FragmentCategoryBinding
import cz.johnyapps.cheers.dto.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        viewModel.category.observe(viewLifecycleOwner, { category ->
            binding.categoryNameTextView.text = category.name
            binding.categoryNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, category.icon.iconId, 0, 0)
        })

        if (category != null) {
            viewModel.setCategory(category as Category)
        }

        return binding.root
    }
}