package com.karsatech.karsanime.ui.anime

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.anime.Genres
import com.karsatech.karsanime.core.ui.GenreAdapter
import com.karsatech.karsanime.databinding.ActivityDetailAnimeBinding

class DetailAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnimeBinding
    private lateinit var data: DetailGeneralResponse
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<DetailGeneralResponse>(DETAIL_ANIME) as DetailGeneralResponse

        Log.d("DetailAnimeActivity", data.toString())

        initializeRecyclerViews()
        setupData(data)

    }

    private fun setupData(data: DetailGeneralResponse) {

        binding.title.text = data.title ?: "unknown"
        binding.rating.text = data.rating ?: "unknown"
        binding.score.text = if (data.score == null) "0.0" else data.score.toString()
        binding.textRanking.text = if (data.rank == null) "unknown" else data.rank.toString()
        binding.textMember.text = if (data.members == null) "unknown" else data.members.toString()
        binding.textPopularity.text = if (data.popularity == null) "unknown" else data.popularity.toString()
        binding.textFavorites.text = if (data.favorite == null) "unknown" else data.favorite.toString()
        binding.synopsis.text = data.synopsis ?: "unknown"
        binding.status.text = data.status ?: "unknown"
        binding.episodes.text = if (data.episodes == null) "unknown" else data.episodes.toString() + " episodes"

        Glide.with(this)
            .load(data.images!!.jpg!!.largeImageUrl)
            .into(binding.imagePoster)

        setGenre(data.genres)
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