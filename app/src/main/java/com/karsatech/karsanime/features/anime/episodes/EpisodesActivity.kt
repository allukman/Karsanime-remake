package com.karsatech.karsanime.features.anime.episodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeItem
import com.karsatech.karsanime.core.ui.AnimeCharacterAdapter
import com.karsatech.karsanime.core.ui.EpisodesAdapter
import com.karsatech.karsanime.databinding.ActivityEpisodesBinding
import com.karsatech.karsanime.features.WebViewActivity
import com.karsatech.karsanime.features.anime.statistic.StatisticActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesActivity : AppCompatActivity() {

    private val episodesViewModel: EpisodesViewModel by viewModels()
    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var episodesAdapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val animeId = intent.getStringExtra(EPISODES_ANIME_ID)
        val animeTitle = intent.getStringExtra(EPISODES_ANIME_TITLE)

        supportActionBar?.title = animeTitle

        initializeRecyclerViews()
        observeViewModel(animeId.toString())
    }

    private fun observeViewModel(id: String) {
        episodesViewModel.getEpisodesAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressAnimeEpisodes)

                is Resource.Success -> {
                    hideLoadingState(binding.progressAnimeEpisodes)
                    anime.data?.data?.let { setAnimeEpisodesData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressAnimeEpisodes)
                    showErrorState(binding.errorAnimeEpisodes, anime.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }

    private fun setAnimeEpisodesData(data: List<EpisodesAnimeItem?>) {
        episodesAdapter.submitList(data)

        episodesAdapter.setOnItemClickCallback(object : EpisodesAdapter.ActionAdapter {
            override fun onTitleClick(data: EpisodesAnimeItem) {
                val intent = Intent(this@EpisodesActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.WEBVIEW_URL, data.url)
                startActivity(intent)
            }

            override fun onForumClick(data: EpisodesAnimeItem) {
                val intent = Intent(this@EpisodesActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.WEBVIEW_URL, data.forumUrl)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvAnimeEpisodes.apply {
            layoutManager = LinearLayoutManager(this@EpisodesActivity, RecyclerView.VERTICAL, false)
            episodesAdapter = EpisodesAdapter()
            adapter = episodesAdapter
        }
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

    private fun showLoadingState(progressBar: ShimmerFrameLayout) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState(progressBar: ShimmerFrameLayout) {
        progressBar.visibility = View.GONE
    }

    private fun showErrorState(errorTextView: TextView, errorMessage: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
    }

    companion object {
        const val EPISODES_ANIME_ID = "EPISODES_ANIME_ID"
        const val EPISODES_ANIME_TITLE = "EPISODES_ANIME_TITLE"
    }
}