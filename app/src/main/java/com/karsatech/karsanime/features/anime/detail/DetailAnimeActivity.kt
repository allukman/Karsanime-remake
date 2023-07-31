package com.karsatech.karsanime.features.anime.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.databinding.ActivityDetailAnimeBinding
import com.karsatech.karsanime.features.anime.full.FullDetailAnimeActivity
import com.karsatech.karsanime.features.anime.full.FullDetailAnimeActivity.Companion.ANIME_ID
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
        binding.textRanking.text = if (data.rank == "null") "0" else "#" + data.rank
        binding.textMember.text = if (data.members == "null") "0" else data.members
        binding.textPopularity.text = if (data.popularity == "null") "0" else data.popularity
        binding.textFavorites.text = if (data.favorites == "null") "0" else data.favorites
        binding.synopsis.text = if (data.synopsis == "null") getString(R.string.error_anime_synopsis) else data.synopsis
        binding.status.text = if (data.status == "null") getString(R.string.error_status) else data.status
        binding.episodes.text = if (data.episodes == "null") "0 episodes" else data.episodes + " episodes"

        Glide.with(this)
            .load(data.image)
            .into(binding.imagePoster)

        setOnClick()

        binding.btnDetail.setOnClickListener {
            val intent = Intent(this, FullDetailAnimeActivity::class.java)
            intent.putExtra(ANIME_ID, data.animeId)
            startActivity(intent)
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

    private fun setOnClick() {
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


    }

    companion object {
        const val DETAIL_ANIME = "DETAIL_ANIME"
    }
}