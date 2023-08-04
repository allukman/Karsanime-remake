package com.karsatech.karsanime.features.anime.review

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.ReviewItem
import com.karsatech.karsanime.core.ui.ReviewAdapter
import com.karsatech.karsanime.databinding.ActivityReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : AppCompatActivity() {

    private val reviewViewModel: ReviewViewModel by viewModels()

    private lateinit var binding: ActivityReviewBinding
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Anime Review"

        val animeId = intent.getStringExtra(REVIEW_ANIME_ID)

        initializeRecyclerViews()
        observeViewModel(animeId.toString())
    }

    private fun initializeRecyclerViews() {
        binding.rvAnimeReview.apply {
            layoutManager = LinearLayoutManager(this@ReviewActivity, RecyclerView.VERTICAL, false)
            reviewAdapter = ReviewAdapter(this)
            adapter = reviewAdapter
        }
    }

    private fun setAnimeReviewData(data: List<ReviewItem?>) {
        reviewAdapter.submitList(data)
    }

    private fun observeViewModel(id: String) {
        reviewViewModel.getReviewAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressAnimeReview)

                is Resource.Success -> {
                    hideLoadingState(binding.progressAnimeReview)
                    anime.data?.data?.let { setAnimeReviewData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressAnimeReview)
                    showErrorState(binding.errorAnimeReview, anime.message ?: getString(R.string.something_wrong))
                }
            }
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
        const val REVIEW_ANIME_ID = "REVIEW_ANIME_ID"
    }
}