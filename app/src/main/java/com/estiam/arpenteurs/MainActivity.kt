package com.estiam.arpenteurs

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.estiam.arpenteurs.databinding.ActivityMainBinding
import com.google.android.gms.maps.SupportMapFragment
import com.estiam.arpenteurs.ui.authentification.LoginFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // redirige l'utilisateur sur la page home si déjà connecté
        redirectToMainFragmentIfIsConnected()
    }

    /**
     * Redirige l'utilisateur vers le fragment home si ce dernier est authentifié
     */
    fun redirectToMainFragmentIfIsConnected() {
        // si l'utilisateur est authentifié
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            // navigue vers le fragment home
            val nav = findNavController(R.id.nav_host_fragment_content_main)
            nav.navigate(R.id.action_LoginFragment_to_MainFragment)
        }
    }

    /**
     * Redirige vers le LoginFragment et déconnecte l'utilisateur
     */
    private fun logoutAndRedirectToLogin() {
        // redirection vers le fragment
        val nav = findNavController(R.id.nav_host_fragment_content_main)
        nav.navigate(R.id.action_to_LoginFragment)

        // timer de 100ms afin d'accéder à la méthode logoutFromGoogle du LoginFragment
        Timer("waitForLoginFragment", false).schedule(100) {
            // récupère le nouveau fragment LoginFragment courant
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                    ?.childFragmentManager
                    ?.primaryNavigationFragment as LoginFragment
            ).logoutFromGoogle()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // au click du menu
    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            // déconnecte l'utilisateur
            R.id.action_logout -> logoutAndRedirectToLogin()
            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}