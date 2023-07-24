package com.karsatech.karsanime.features.manga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.Genres
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.ui.GenreAdapter
import com.karsatech.karsanime.databinding.ActivityDetailMangaBinding
import com.karsatech.karsanime.features.anime.DetailAnimeViewModel
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMangaActivity : AppCompatActivity() {

    private val detailMangaViewModel: DetailMangaViewModel by viewModels()
    private lateinit var binding: ActivityDetailMangaBinding
    private lateinit var data: DetailGeneralResponse
    private lateinit var genreAdapter: GenreAdapter
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<DetailGeneralResponse>(DETAIL_MANGA) as DetailGeneralResponse

        initializeRecyclerViews()
        setupData(data)
    }

    private fun setupData(data: DetailGeneralResponse) {

        binding.title.text = data.title ?: "unknown"
        binding.score.text = if (data.score == null) "0.0" else data.score.toString()
        binding.textRanking.text = if (data.rank == null) "0" else data.rank.toString()
        binding.textMember.text = if (data.members == null) "0" else data.members.toString()
        binding.textPopularity.text = if (data.popularity == null) "0" else data.popularity.toString()
        binding.textFavorites.text = if (data.favorite == null) "0" else data.favorite.toString()
        binding.synopsis.text = data.synopsis ?: getString(R.string.error_anime_synopsis)
        binding.status.text = data.status ?: getString(R.string.error_status)
        binding.chapters.text = if (data.episodes == null) "0 chapters" else data.episodes.toString() + " chapters"
        binding.volumes.text = if (data.volumes == null) "0 volumes" else data.volumes.toString() + " volumes"

        Glide.with(this)
            .load(data.images!!.jpg!!.largeImageUrl)
            .into(binding.imagePoster)

        setGenre(data.genres)

        binding.imagePoster.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(DETAIL_IMAGE, data.images.jpg!!.largeImageUrl)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnFavorite.setOnClickListener {
            val favManga = Manga(
                mangaId = data.malId.toString(),
                title = data.title.toString(),
                image = data.images.jpg?.largeImageUrl.toString(),
                chapters = data.chapters.toString(),
                volumes = data.volumes.toString(),
                status = data.status.toString()
            )
            if (isFavorite) {
                detailMangaViewModel.setUnFavoriteManga(data.malId.toString())
            } else {
                detailMangaViewModel.setFavoriteManga(favManga)
            }
        }

        detailMangaViewModel.getFavoriteMangaByMalId(data.malId.toString()).observe(this) { listManga ->
            isFavorite = if (listManga.isEmpty()) {
                Log.d("DetailAnimeActivity", "Not Favorite")
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                Log.d("DetailAnimeActivity", "Favorite")
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }
    }

    private fun setGenre(data: ArrayList<Genres>) {
        genreAdapter.submitList(data)
    }

    private fun initializeRecyclerViews() {
        binding.rvGenre.apply {
            layoutManager = FlexboxLayoutManager(this@DetailMangaActivity)
            genreAdapter = GenreAdapter()
            adapter = genreAdapter
        }
    }

    companion object {
        const val DETAIL_MANGA = "DETAIL_MANGA"
    }
}