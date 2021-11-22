package cz.johnyapps.cheers.categories

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.johnyapps.cheers.category.CategoryFragment

class CategoryFragmentAdapter(
    parentFragment: Fragment,
    private val categories: List<Category>
): FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment(categories[position])
    }
}