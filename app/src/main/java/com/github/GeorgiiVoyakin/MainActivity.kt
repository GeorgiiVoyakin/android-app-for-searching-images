package com.github.GeorgiiVoyakin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.registrationFragment,
                R.id.settingsFragment,
                R.id.galleryFragment,
                R.id.searchFragment,
                R.id.favouritesFragment,
                R.id.albumsFragment,
            )
        )
        topAppBar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(topAppBar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility =
                if (destination.id == R.id.loginFragment ||
                    destination.id == R.id.registrationFragment ||
                    destination.id == R.id.settingsFragment
                ) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            Log.d(TAG, "onCreate: navController.addOnDestinationChangedListener")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_menu_item -> {
                navController.navigate(R.id.settingsFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}