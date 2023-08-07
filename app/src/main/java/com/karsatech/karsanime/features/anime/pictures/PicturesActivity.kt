package com.karsatech.karsanime.features.anime.pictures

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.PictureItem
import com.karsatech.karsanime.core.ui.PicturesAdapter
import com.karsatech.karsanime.databinding.ActivityPicturesBinding
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturesActivity : AppCompatActivity() {

    private val picturesViewModel: PicturesViewModel by viewModels()
    private lateinit var binding: ActivityPicturesBinding
    private lateinit var picturesAdapter: PicturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Anime Pictures"

        val animeId = intent.getStringExtra(PICTURES_ANIME_ID)

        initializeRecyclerViews()
        observeViewModel(animeId.toString())
    }

    private fun initializeRecyclerViews() {
        binding.rvAnimePictures.apply {
            layoutManager = GridLayoutManager(this@PicturesActivity, 3)
            picturesAdapter = PicturesAdapter(this@PicturesActivity)
            adapter = picturesAdapter
        }
    }

    private fun setAnimePicturesData(data: List<PictureItem?>) {
        picturesAdapter.submitList(data)

        picturesAdapter.setOnItemClickCallback(object : PicturesAdapter.ActionAdapter {
            override fun onClick(data: PictureItem) {
                val intent = Intent(this@PicturesActivity, ImageActivity::class.java)
                intent.putExtra(DETAIL_IMAGE, data.jpg?.largeImageUrl)
                startActivity(intent)
            }
        })

    }

    private fun observeViewModel(id: String) {
        picturesViewModel.getPicturesAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressAnimePictures)

                is Resource.Success -> {
                    hideLoadingState(binding.progressAnimePictures)
                    anime.data?.data?.let { setAnimePicturesData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressAnimePictures)
                    showErrorState(binding.errorAnimePictures, anime.message ?: getString(R.string.something_wrong))
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

    private fun showLoadingState(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    private fun showErrorState(errorTextView: TextView, errorMessage: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
    }

    companion object {
        const val PICTURES_ANIME_ID = "PICTURES_ANIME_ID"
    }
}