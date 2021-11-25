package cz.johnyapps.cheers.global.fragments

interface OnBackSupportFragment {
    /**
     * @return True if handled
     */
    fun onBackPressed(): Boolean
}