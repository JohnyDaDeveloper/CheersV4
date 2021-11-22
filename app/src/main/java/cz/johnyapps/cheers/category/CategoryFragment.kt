package cz.johnyapps.cheers.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.categories.Category
import cz.johnyapps.cheers.databinding.FragmentCategoryBinding

class CategoryFragment(
    private var category: Category
): Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        binding.categoryNameTextView.text = category.name
        binding.categoryNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, category.icon.iconId, 0, 0)

        return binding.root
    }
}