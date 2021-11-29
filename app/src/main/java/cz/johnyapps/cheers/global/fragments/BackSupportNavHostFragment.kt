package cz.johnyapps.cheers.global.fragments

import androidx.navigation.fragment.NavHostFragment

class BackSupportNavHostFragment: NavHostFragment(), OnBackSupportFragment {
    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.fragments[0]

        if (fragment is OnBackSupportFragment) {
            return fragment.onBackPressed()
        }

        return false
    }
}