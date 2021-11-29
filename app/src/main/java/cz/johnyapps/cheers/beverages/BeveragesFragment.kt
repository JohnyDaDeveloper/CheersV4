package cz.johnyapps.cheers.beverages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.johnyapps.cheers.databinding.FragmentBeveragesBinding

class BeveragesFragment: Fragment() {
    private lateinit var binding: FragmentBeveragesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeveragesBinding.inflate(inflater, container, false)

        return binding.root
    }
}