package com.karsatech.karsanime.features.manga

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.databinding.ActivityDetailMangaBinding
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMangaActivity : AppCompatActivity() {

    private val detailMangaViewModel: DetailMangaViewModel by viewModels()
    private lateinit var binding: ActivityDetailMangaBinding
    private lateinit var data: Manga
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<Manga>(DETAIL_MANGA) as Manga

        setupData(data)
    }

    private fun setupData(data: Manga) {

        binding.title.text = if (data.title == "null") "n/a" else data.title
        binding.score.text = if (data.score == "null") "0.0" else data.score
        binding.textRanking.text = if (data.rank == "null") "0" else "#" + data.rank
        binding.textMember.text = if (data.members == "null") "0" else data.members
        binding.textPopularity.text = if (data.popularity == "null") "0" else data.popularity
        binding.textFavorites.text = if (data.favorites == "null") "0" else data.favorites
        binding.synopsis.text = if (data.synopsis == "null") getString(R.string.error_anime_synopsis) else data.synopsis
        binding.status.text = if (data.status == "null") getString(R.string.error_status) else data.status
        binding.chapters.text = if (data.chapters == "null") "0 chapters" else data.chapters + " chapters"
        binding.volumes.text = if (data.volumes == "null") "0 volumes" else data.volumes + " volumes"

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
                detailMangaViewModel.setUnFavoriteManga(data.mangaId)
            } else {
                detailMangaViewModel.setFavoriteManga(data)
            }
        }

        detailMangaViewModel.getFavoriteMangaByMalId(data.mangaId).observe(this) { listManga ->
            isFavorite = if (listManga.isEmpty()) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }
    }

    companion object {
        const val DETAIL_MANGA = "DETAIL_MANGA"
    }
}