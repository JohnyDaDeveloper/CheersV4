package cz.johnyapps.cheers.global.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import cz.johnyapps.cheers.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

@AndroidEntryPoint
abstract class NavigationActivity: AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beforeSetContentView()
        setContentView()

        appBarConfiguration = AppBarConfiguration.Builder(getNavController().graph)
            .setOpenableLayout(getDrawerLayout())
            .build()

        getNavView().setupWithNavController(getNavController())
        getToolbar().setupWithNavController(getNavController(), appBarConfiguration)
    }

    abstract fun getToolbar(): Toolbar
    abstract fun beforeSetContentView()
    abstract fun setContentView()
    abstract fun getDrawerLayout(): DrawerLayout
    abstract fun getNavView(): NavigationView

    fun getNavHostFragment(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    fun getNavController(): NavController {
        return getNavHostFragment().navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = getNavController()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            getNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawer = getDrawerLayout()

        if (drawer.isOpen) {
            drawer.close()
        } else {
            super.onBackPressed()
        }
    }
}