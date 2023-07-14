package com.karsatech.karsanime

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.karsatech.karsanime.anime.AnimeFragment
import com.karsatech.karsanime.databinding.ActivityMainBinding
import com.karsatech.karsanime.favorite.FavoriteFragment
import com.karsatech.karsanime.home.HomeFragment
import com.karsatech.karsanime.manga.MangaFragment
import com.karsatech.karsanime.people.PeopleFragment
import com.karsatech.karsanime.setting.SettingFragment

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
                fragment = FavoriteFragment()
                title = getString(R.string.menu_favorite)
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