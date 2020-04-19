package com.cerridan.androidtemplate.activity

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.cerridan.androidtemplate.R
import com.cerridan.androidtemplate.util.bindView

class MainActivity : AppCompatActivity() {

    private val drawerLayout: DrawerLayout by bindView(R.id.dl_main_container)
    private val navigationView: NavigationView by bindView(R.id.nv_main_navigation)
    private val toolbar: Toolbar by bindView(R.id.tb_main_toolbar)

    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.nav_home), drawerLayout) }
    private val navigationController get() = findNavController(R.id.f_main_nav_host)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        navigationView.setupWithNavController(navigationController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_actionbar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean =
        navigationController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
