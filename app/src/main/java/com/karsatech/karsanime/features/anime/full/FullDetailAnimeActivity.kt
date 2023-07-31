package com.karsatech.karsanime.features.anime.full

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeResponse
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityFullDetailAnimeBinding
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.detail.DetailAnimeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullDetailAnimeActivity : AppCompatActivity() {

    private val fullDetailAnimeViewModel: FullDetailAnimeViewModel by viewModels()
    private lateinit var binding: ActivityFullDetailAnimeBinding
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getStringExtra(ANIME_ID)

        lifecycle.addObserver(binding.youtubePlayer)

        setOnClick()
        observeViewModel(animeId.toString())


    }

    private fun observeViewModel(id: String) {
        fullDetailAnimeViewModel.getFullDetailAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> Log.d("FullDetailAnime", "Loading")

                is Resource.Success -> {
                    setFullDataAnime(anime.data?.data!!)
                    Log.d("FullDetailAnime", anime.data.toString())
                }

                is Resource.Error -> {
                    Log.d("FullDetailAnime", "Error")
                }
            }
        }
    }

    private fun setFullDataAnime(data: DetailAnimeItem) {

        Glide.with(this)
            .load(data.trailer?.images?.largeImageUrl)
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .dontAnimate()
            .into(binding.imageLandscape)

        Glide.with(this)
            .load(data.images?.jpg?.largeImageUrl)
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .dontAnimate()
            .into(binding.imagePotrait)

        binding.title.text = data.title ?: ""
        binding.rating.text = data.rating ?: ""
        binding.type.text = data.type ?: ""
        binding.year.text = if (data.year != null) data.year.toString() else ""
        binding.season.text = data.season ?: ""
        binding.airing.text = data.aired?.string ?: ""
        binding.textRanking.text = if (data.rank != null) data.rank.toString() else ""
        binding.textPopularity.text = if (data.popularity != null) data.popularity.toString() else ""
        binding.textMember.text = if (data.members != null) data.members.toString() else ""
        binding.textFavorites.text = if (data.favorites != null) data.favorites.toString() else ""
        binding.synopsis.text = data.synopsis ?: ""
        binding.infoTitle.text = data.title ?: ""
        binding.infoTitleEnglish.text = data.titleEnglish ?: ""
        binding.infoTitleJapanese.text = data.titleJapanese ?: ""
        binding.infoSource.text = data.source ?: ""
        binding.infoEpisodes.text = if (data.episodes != null) data.episodes.toString() else ""
        binding.infoAiring.text = if (data.airing!!) "airing" else "not airing"
        binding.infoAiringFrom.text = data.aired?.from?.withDateFormat()
        binding.infoAiringTo.text = data.aired?.to?.withDateFormat()
        binding.infoDuration.text = data.duration ?: ""
        binding.infoScore.text = "${data.score} (${data.scoredBy})"

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "Nu9NnD9f03k"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        fullDetailAnimeViewModel.getFavoriteByMalId(data.malId.toString()).observe(this) { listAnime ->
            isFavorite = if (listAnime.isEmpty()) {
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }

        val anime = DataMapper.apiResponseToAnimeModel(data)
        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                fullDetailAnimeViewModel.setUnFavorite(data.malId.toString())
            } else {
                fullDetailAnimeViewModel.setFavorite(anime)
            }
        }
    }

    private fun setOnClick() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val ANIME_ID = "ANIME_ID"
    }
}