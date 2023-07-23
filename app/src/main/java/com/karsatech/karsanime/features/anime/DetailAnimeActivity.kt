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
    private lateinit var data: DetailGeneralResponse
    private lateinit var genreAdapter: GenreAdapter
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data =
            intent.getParcelableExtra<DetailGeneralResponse>(DETAIL_ANIME) as DetailGeneralResponse

        initializeRecyclerViews()
        setupData(data)

    }

    private fun setupData(data: DetailGeneralResponse) {

        binding.title.text = data.title ?: "unknown"
        binding.rating.text = data.rating ?: "unknown"
        binding.score.text = if (data.score == null) "0.0" else data.score.toString()
        binding.textRanking.text = if (data.rank == null) "0" else data.rank.toString()
        binding.textMember.text = if (data.members == null) "0" else data.members.toString()
        binding.textPopularity.text =
            if (data.popularity == null) "0" else data.popularity.toString()
        binding.textFavorites.text = if (data.favorite == null) "0" else data.favorite.toString()
        binding.synopsis.text = data.synopsis ?: getString(R.string.error_anime_synopsis)
        binding.status.text = data.status ?: getString(R.string.error_status)
        binding.episodes.text =
            if (data.episodes == null) "0 episodes" else data.episodes.toString() + " episodes"

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
            val favAnime = Anime(
                animeId = data.malId.toString(),
                title = data.title.toString(),
                image = data.images.jpg?.largeImageUrl.toString(),
                synopsis = data.synopsis.toString()
            )
            if (isFavorite) {
                detailAnimeViewModel.setUnFavorite(data.malId.toString())
            } else {
                detailAnimeViewModel.setFavorite(favAnime)
            }
        }

        detailAnimeViewModel.getFavoriteByMalId(data.malId.toString()).observe(this) { listAnime ->
            isFavorite = if (listAnime.isEmpty()) {
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
            layoutManager = FlexboxLayoutManager(this@DetailAnimeActivity)
            genreAdapter = GenreAdapter()
            adapter = genreAdapter
        }
    }

    companion object {
        const val DETAIL_ANIME = "DETAIL_ANIME"
    }
}