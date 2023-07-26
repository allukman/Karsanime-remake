package com.karsatech.karsanime.features.search

import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.utils.DataType
import com.karsatech.karsanime.core.utils.FilterType
import com.karsatech.karsanime.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    var searchType = FilterType.ANIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchView()
        initOnClick()
    }

    private fun initSearchView() {
        binding.searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchActivity", query.toString())
                binding.searchButton.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showSortingPopMenu() {

        PopupMenu(this, binding.btnFilter).run {
            menuInflater.inflate(R.menu.option_menu, menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.anime -> {
                        Toast.makeText(this@SearchActivity, "Anime", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.ANIME
                        true
                    }

                    R.id.manga -> {
                        Toast.makeText(this@SearchActivity, "Manga", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.MANGA
                        true
                    }

                    R.id.character -> {
                        Toast.makeText(this@SearchActivity, "Character", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.CHARACTER
                        true
                    }

                    R.id.people -> {
                        Toast.makeText(this@SearchActivity, "People", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.PEOPLE
                        true
                    }

                    else -> false
                }
            }
            show()
        }
    }

    private fun initOnClick() {
//        binding.btnBack.setOnClickListener {
//            onBackPressedDispatcher.onBackPressed()
//        }
        binding.btnFilter.setOnClickListener {
            showSortingPopMenu()
        }
    }
}