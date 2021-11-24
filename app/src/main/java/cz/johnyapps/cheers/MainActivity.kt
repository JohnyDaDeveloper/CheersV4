package cz.johnyapps.cheers

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.BeverageDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import cz.johnyapps.cheers.databinding.ActivityMainBinding
import cz.johnyapps.cheers.dto.Beverage
import cz.johnyapps.cheers.dto.Counter
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
                0,
                0
            ))

            database.categoryDao().insert(CategoryDbEntity(
                2,
                "Wine",
                Icon.WINE,
                0,
                1
            ))

            val beverage = Beverage(1, "Beer", 4f, Color.RED, Color.BLACK, null)
            database.beverageDao().insert(BeverageDbEntity(beverage))

            val counter = Counter(beverage, 0.5f)
            counter.id = 1
            database.counterDao().insert(CounterDbEntity(counter))
        }
    }
}