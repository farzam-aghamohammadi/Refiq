package com.eth.refiq

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.eth.refiq.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)


        setNavigationGraph(navGraph, navController, navView)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            println("gggg ${R.id.navigation_home}")
            if (destination.id == R.id.navigation_home) {
                navView.isGone = false
            } else {
                navView.isGone = true
            }
        }
    }

    private fun setNavigationGraph(
        navGraph: NavGraph,
        navController: NavController,
        navView: BottomNavigationView
    ) {

        navGraph.setStartDestination(R.id.navigation_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_wallet
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.graph = navGraph


    }

    private fun setConnectWalletUI(
        navGraph: NavGraph,
        navController: NavController,
        navView: BottomNavigationView
    ) {
        navGraph.setStartDestination(R.id.navigation_connect_wallet)
        navView.isGone = true

        navController.graph = navGraph
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


        println("new Intent ${intent?.data} : ${intent} : ${intent}")
    }

    private fun setUpLoggedInUI() {

    }
}