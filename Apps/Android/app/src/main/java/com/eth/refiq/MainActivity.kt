package com.eth.refiq

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.findNavController
import com.eth.refiq.databinding.ActivityMainBinding
import com.walletconnect.web3.modal.ui.openWeb3Modal
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setConnectWalletUI()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navGraph=navController.navInflater.inflate(R.navigation.mobile_navigation)


        navGraph.setStartDestination(R.id.navigation_connect_wallet)
        navView.isGone=true

        navController.graph = navGraph
        /*val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
    private fun setNavigationGraph() {

    }
    private fun setConnectWalletUI(){
        findNavController(R.id.nav_host_fragment_activity_main).graph.setStartDestination(R.id.navigation_connect_wallet)

    }
    private fun setUpLoggedInUI(){

    }
}