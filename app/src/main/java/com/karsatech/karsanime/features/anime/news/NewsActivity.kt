package com.karsatech.karsanime.features.anime.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.EpisodesAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.NewsItem
import com.karsatech.karsanime.core.ui.EpisodesAdapter
import com.karsatech.karsanime.core.ui.NewsAdapter
import com.karsatech.karsanime.databinding.ActivityNewsBinding
import com.karsatech.karsanime.features.WebViewActivity
import com.karsatech.karsanime.features.anime.episodes.EpisodesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Anime News"

        val animeId = intent.getStringExtra(NEWS_ANIME_ID)

        initializeRecyclerViews()
        observeViewModel(animeId.toString())

    }

    private fun observeViewModel(id: String) {
        newsViewModel.getNewsAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressAnimeNews)

                is Resource.Success -> {
                    hideLoadingState(binding.progressAnimeNews)
                    anime.data?.data?.let { setAnimeEpisodesData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressAnimeNews)
                    showErrorState(binding.errorAnimeNews, anime.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }

    private fun setAnimeEpisodesData(data: List<NewsItem?>) {
        newsAdapter.submitList(data)

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.ActionAdapter {
            override fun onNewsClick(data: NewsItem) {
                val intent = Intent(this@NewsActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.WEBVIEW_URL, data.url)
                startActivity(intent)
            }

            override fun onForumClick(data: NewsItem) {
                val intent = Intent(this@NewsActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.WEBVIEW_URL, data.forumUrl)
                startActivity(intent)
            }

            override fun onAuthorClick(data: NewsItem) {
                val intent = Intent(this@NewsActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.WEBVIEW_URL, data.authorUrl)
                startActivity(intent)
            }

        })

    }

    private fun initializeRecyclerViews() {
        binding.rvAnimeNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity, RecyclerView.VERTICAL, false)
            newsAdapter = NewsAdapter()
            adapter = newsAdapter
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
        const val NEWS_ANIME_ID = "NEWS_ANIME_ID"
    }
}