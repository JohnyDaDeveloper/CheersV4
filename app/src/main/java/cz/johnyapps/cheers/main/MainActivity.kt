package cz.johnyapps.cheers.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.global.enums.Icon
import cz.johnyapps.cheers.R
import cz.johnyapps.cheers.global.enums.Sound
import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.databinding.ActivityMainBinding
import cz.johnyapps.cheers.global.activities.NavigationActivity
import cz.johnyapps.cheers.global.fragments.OnBackSupportFragment
import cz.johnyapps.cheers.global.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NavigationActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var database: BeverageDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            database.categoryDao().insert(CategoryDbEntity(
                1,
                "Beer",
                Icon.BEER,
                listOf(Sound.BEER1, Sound.BEER2),
                0,
                0
            ))

            database.categoryDao().insert(CategoryDbEntity(
                2,
                "Wine",
                Icon.WINE,
                listOf(Sound.WINE1, Sound.WINE2),
                0,
                1
            ))

            database.categoryDao().insert(CategoryDbEntity(
                3,
                "Shots",
                Icon.SHOT,
                emptyList(),
                0,
                2
            ))
        }
    }

    override fun getToolbar(): Toolbar {
        return binding.toolbar
    }

    override fun beforeSetContentView() {
        installSplashScreen()
    }

    override fun setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.setSupportActionBar(binding.toolbar)
    }

    override fun getDrawerLayout(): DrawerLayout {
        return binding.drawerLayout
    }

    override fun onBackPressed() {
        //TODO: Fix after navigation implementation
        val fragment: Fragment = binding.navHostFragment.getFragment()

        if (fragment !is OnBackSupportFragment || !fragment.onBackPressed()) {
            super.onBackPressed()
        }
    }
}