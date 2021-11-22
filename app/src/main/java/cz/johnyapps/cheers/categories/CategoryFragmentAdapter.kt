package cz.johnyapps.cheers.categories

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.johnyapps.cheers.category.CategoryFragment
import cz.johnyapps.cheers.dto.Category

class CategoryFragmentAdapter(
    parentFragment: Fragment
): FragmentStateAdapter(parentFragment) {
    private val categories: MutableList<Category> = ArrayList()

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment(categories[position])
    }

    fun submitList(list: List<Category>) {
        //TODO Optimize!
        categories.clear()
        categories.addAll(list)
        notifyDataSetChanged()
    }
}