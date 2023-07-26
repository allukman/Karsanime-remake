package com.karsatech.karsanime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.karsatech.karsanime.features.anime.AnimeFragment
import com.karsatech.karsanime.databinding.ActivityMainBinding
import com.karsatech.karsanime.features.favorite.FavoriteActivity
import com.karsatech.karsanime.features.home.HomeFragment
import com.karsatech.karsanime.features.manga.MangaFragment
import com.karsatech.karsanime.features.people.PeopleFragment
import com.karsatech.karsanime.features.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
            supportActionBar?.title = getString(R.string.app_name)
        }

        Log.d("MainActivity", "onCreate()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop()")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = getString(R.string.app_name)
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
                title = getString(R.string.app_name)
            }
            R.id.nav_anime -> {
                fragment = AnimeFragment()
                title = getString(R.string.menu_anime)
            }
            R.id.nav_manga -> {
                fragment = MangaFragment()
                title = getString(R.string.menu_manga)
            }
            R.id.nav_people -> {
                fragment = PeopleFragment()
                title = getString(R.string.menu_people)
            }
            R.id.nav_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_setting -> {
                fragment = SettingFragment()
                title = getString(R.string.menu_setting)
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }
        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}