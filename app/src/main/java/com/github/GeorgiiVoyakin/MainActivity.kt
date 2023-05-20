package com.github.GeorgiiVoyakin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
//        setSupportActionBar(topAppBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        NavigationUI.setupActionBarWithNavController(this, navController)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//            .setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility =
                if (destination.id == R.id.loginFragment ||
                    destination.id == R.id.registrationFragment
                ) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            Log.d(TAG, "onCreate: navController.addOnDestinationChangedListener")
        }

//        topAppBar.setNavigationOnClickListener {
//            super.onSupportNavigateUp()
//            navController.navigateUp()
//        }
//
//        topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.settings_menu_item -> {
//                    Log.d(TAG, "onCreate: settings clicked")
//                    true
//                }
//
//                else -> false
//            }
//        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item1 -> {
                    Log.d(TAG, "onCreate: gallery nav clicked")
                    navController.navigate(R.id.galleryFragment)
                    true
                }

                R.id.menu_item2 -> {
                    Log.d(TAG, "onCreate: search nav clicked")
                    navController.navigate(R.id.searchFragment)
                    true
                }

                R.id.menu_item3 -> {
                    Log.d(TAG, "onCreate: fav nav clicked")
                    navController.navigate(R.id.favouritesFragment)
                    true
                }

                R.id.menu_item4 -> {
                    Log.d(TAG, "onCreate: albums nav clicked")
                    navController.navigate(R.id.albumsFragment)
                    true
                }

                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}