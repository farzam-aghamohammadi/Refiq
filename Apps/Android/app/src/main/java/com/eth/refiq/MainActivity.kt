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
import com.eth.refiq.ui.AwardViewModel
import com.eth.refiq.ui.add.content.AddContentViewModel
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val walletViewModel: WalletViewModel by viewModel()
    private val awardViewModel: AwardViewModel by viewModel()
    private val addContentViewModel: AddContentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walletViewModel.init()
        awardViewModel.init()
        addContentViewModel.init()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)


        setNavigationGraph(navGraph, navController, navView)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            navView.isGone =
                !(destination.id == R.id.navigation_home || destination.id == R.id.navigation_wallet || destination.id == R.id.navigation_new)
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
                R.id.navigation_home, R.id.navigation_new, R.id.navigation_wallet
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.graph = navGraph


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


        println("add Intent ${intent?.data} : ${intent} : ${intent}")
    }

}