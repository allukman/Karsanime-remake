package com.karsatech.karsanime.features.anime.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.CharacterAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.ui.AnimeAdapter
import com.karsatech.karsanime.core.ui.AnimeCharacterAdapter
import com.karsatech.karsanime.core.ui.PeopleAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.databinding.ActivityCharacterBinding
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.statistic.StatisticActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModels()
    private lateinit var binding: ActivityCharacterBinding

    private lateinit var animeCharacterAdapter: AnimeCharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val animeId = intent.getStringExtra(CHARACTER_ANIME_ID)
        val animeTitle = intent.getStringExtra(CHARACTER_ANIME_TITLE)

        supportActionBar?.title = animeTitle

        initializeRecyclerViews()
        observeViewModel(animeId.toString())

    }

    private fun initializeRecyclerViews() {
        binding.rvAnimeCharacter.apply {
            layoutManager = LinearLayoutManager(this@CharacterActivity, RecyclerView.VERTICAL, false)
            animeCharacterAdapter = AnimeCharacterAdapter()
            adapter = animeCharacterAdapter
        }
    }

    private fun setAnimeCharacterData(data: List<CharacterAnimeItem?>) {
        animeCharacterAdapter.submitList(data)

        animeCharacterAdapter.setOnItemClickCallback(object : AnimeCharacterAdapter.ActionAdapter {
            override fun onItemClick(data: CharacterAnimeItem) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun observeViewModel(id: String) {
        characterViewModel.getCharacterAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressAnimeCharacter)

                is Resource.Success -> {
                    hideLoadingState(binding.progressAnimeCharacter)
                    anime.data?.data?.let { setAnimeCharacterData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressAnimeCharacter)
                    showErrorState(binding.errorAnimeCharacter, anime.message ?: getString(R.string.something_wrong))
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
        const val CHARACTER_ANIME_ID = "CHARACTER_ANIME_ID"
        const val CHARACTER_ANIME_TITLE = "CHARACTER_ANIME_TITLE"
    }
}