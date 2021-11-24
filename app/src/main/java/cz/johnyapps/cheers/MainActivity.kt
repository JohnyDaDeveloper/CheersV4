package cz.johnyapps.cheers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var database: BeverageDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.setSupportActionBar(binding.toolbar)

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
}