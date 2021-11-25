package cz.johnyapps.cheers.categories

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.johnyapps.cheers.category.CategoryFragment
import cz.johnyapps.cheers.global.dto.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class CategoryFragmentAdapter(
    parentFragment: Fragment
): FragmentStateAdapter(parentFragment) {
    private val categories: MutableList<Category> = ArrayList()
    private val fragments = ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = CategoryFragment(categories[position])
        fragments.add(fragment)
        return fragment
    }

    fun getFragment(pos: Int): Fragment? {
        return if (pos < fragments.size && pos >= 0) {
            fragments[pos]
        } else {
            null
        }
    }

    fun submitList(list: List<Category>) {
        //TODO Optimize!
        categories.clear()
        categories.addAll(list)
        notifyDataSetChanged()
    }
}