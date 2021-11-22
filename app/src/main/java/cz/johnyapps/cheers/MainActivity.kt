package cz.johnyapps.cheers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var database: BeverageDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*lifecycleScope.launch(Dispatchers.IO) {
            database.categoryDao().insert(CategoryDbEntity(
                0,
                "Beer",
                Icon.BEER,
                0,
                0
            ))

            database.categoryDao().insert(CategoryDbEntity(
                0,
                "Wine",
                Icon.WINE,
                0,
                1
            ))
        }*/
    }
}