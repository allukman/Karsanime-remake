package com.karsatech.karsanime.features.anime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.Genres
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.ui.GenreAdapter
import com.karsatech.karsanime.databinding.ActivityDetailAnimeBinding
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAnimeActivity : AppCompatActivity() {

    private val detailAnimeViewModel: DetailAnimeViewModel by viewModels()
    private lateinit var binding: ActivityDetailAnimeBinding
    private lateinit var data: Anime
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<Anime>(DETAIL_ANIME) as Anime
        setupData(data)
    }

    private fun setupData(data: Anime) {

        binding.title.text = if (data.title == "null") "n/a" else data.title
        binding.rating.text = if (data.rating == "null") "n/a" else data.rating
        binding.score.text = if (data.score == "null") "0.0" else data.score
        binding.textRanking.text = if (data.rank == "null") "0" else data.rank
        binding.textMember.text = if (data.members == "null") "0" else data.members
        binding.textPopularity.text = if (data.popularity == "null") "0" else data.popularity
        binding.textFavorites.text = if (data.favorites == "null") "0" else data.favorites
        binding.synopsis.text = if (data.synopsis == "null") getString(R.string.error_anime_synopsis) else data.synopsis
        binding.status.text = if (data.status == "null") getString(R.string.error_status) else data.status
        binding.episodes.text = if (data.episodes == "null") "0 episodes" else data.episodes + " episodes"

        Glide.with(this)
            .load(data.image)
            .into(binding.imagePoster)

        binding.imagePoster.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(DETAIL_IMAGE, data.image)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                detailAnimeViewModel.setUnFavorite(data.animeId)
            } else {
                detailAnimeViewModel.setFavorite(data)
            }
        }

        detailAnimeViewModel.getFavoriteByMalId(data.animeId).observe(this) { listAnime ->
            isFavorite = if (listAnime.isEmpty()) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }
    }

    companion object {
        const val DETAIL_ANIME = "DETAIL_ANIME"
    }
}