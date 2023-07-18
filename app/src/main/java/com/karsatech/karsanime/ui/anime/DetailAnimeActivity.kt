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

        binding.title.text = data.title
        binding.rating.text = data.rating
        binding.score.text = data.score.toString()
        binding.textRanking.text = data.rank.toString()
        binding.textMember.text = data.members.toString()
        binding.textPopularity.text = data.popularity.toString()
        binding.textFavorites.text = data.favorite.toString()
        binding.synopsis.text = data.synopsis

        Log.d("DetailAnimeActivity", data.genres.toString())
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