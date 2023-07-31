package com.karsatech.karsanime.features.anime.season

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.paging.LoadingStateAdapter
import com.karsatech.karsanime.core.ui.ListAnimeAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.SeasonType
import com.karsatech.karsanime.core.utils.UiUtils
import com.karsatech.karsanime.core.utils.UiUtils.capitalizeFirstLetter
import com.karsatech.karsanime.databinding.ActivityAnimeSeasonBinding
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity.Companion.DETAIL_ANIME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeSeasonActivity : AppCompatActivity() {

    private val animeSeasonViewModel: AnimeSeasonViewModel by viewModels()
    private lateinit var binding: ActivityAnimeSeasonBinding
    private lateinit var listAnimeAdapter: ListAnimeAdapter

    private var season: String? = null
    private var year: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeSeasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initializeRecyclerViews()
        setSpinnerSeason()
        setSpinnerYear()
//
//        observeViewModelAnime(2022, "Summer")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecyclerViews() {
        binding.rvListAnime.apply {
            layoutManager =
                LinearLayoutManager(this@AnimeSeasonActivity, RecyclerView.VERTICAL, false)
            listAnimeAdapter = ListAnimeAdapter()
            adapter = listAnimeAdapter
        }
    }

    private fun setSpinnerSeason() {
        val spinnerSeason =
            arrayOf(SeasonType.Winter, SeasonType.Spring, SeasonType.Summer, SeasonType.Fall)
        binding.spinnerSeason.adapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            spinnerSeason
        )
        binding.spinnerSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                season = when (spinnerSeason[position]) {
                    SeasonType.Winter -> "winter"
                    SeasonType.Spring -> "spring"
                    SeasonType.Summer -> "summer"
                    SeasonType.Fall -> "fall"
                }
                if (season != null && year != null) {
                    supportActionBar?.title = "${season!!.capitalizeFirstLetter()} $year"
                    initAnimeData(year!!,season!!)
                }
            }
        }
    }

    private fun setSpinnerYear() {
        val spinnerYear: ArrayList<String> = ArrayList()
        val thisYear = UiUtils.getCurrentYear() + 1
        val maxyear = 1917

        for (i in thisYear downTo maxyear) {
            spinnerYear.add(i.toString())
        }
        binding.spinnerYear.adapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            spinnerYear
        )
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                year = spinnerYear[position]
                if (season != null && year != null) {
                    supportActionBar?.title = "${season!!.capitalizeFirstLetter()} $year"
                    initAnimeData(year!!, season!!)
                }
            }
        }
    }

    private fun initAnimeData(year: String, season: String) {
        listAnimeLoadingState()
        observeViewModelAnime(year, season)
        binding.rvListAnime.adapter = listAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listAnimeAdapter.retry() }
        )
        setAnimeData()
    }

    private fun observeViewModelAnime(year: String, season: String) {
        animeSeasonViewModel.getAnimeSeasonal(year, season).observe(this) { anime ->
            listAnimeAdapter.submitData(lifecycle, anime)
        }
    }

    private fun listAnimeLoadingState() {
        listAnimeAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListAnime, binding.progressBarAnime, it)
        }
    }

    private fun handleLoadingState(
        recyclerView: RecyclerView,
        progressBar: View,
        loadState: CombinedLoadStates
    ) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }

            is LoadState.NotLoading -> {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            is LoadState.Error -> {
                // Handle error state here if needed
            }
        }
    }

    private fun setAnimeData() {
        listAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                val anime = DataMapper.apiResponseToAnimeModel(data)
                val intent = Intent(this@AnimeSeasonActivity, DetailAnimeActivity::class.java)
                intent.putExtra(DETAIL_ANIME, anime)
                startActivity(intent)
            }
        })
    }
}