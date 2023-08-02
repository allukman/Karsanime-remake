package com.karsatech.karsanime.features.anime.full

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.RecommendationItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.AnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.anime.ExternalItem
import com.karsatech.karsanime.core.ui.ExternalAdapter
import com.karsatech.karsanime.core.ui.GenreAdapter
import com.karsatech.karsanime.core.ui.RecommendationAnimeAdapter
import com.karsatech.karsanime.core.ui.ThemeAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityFullDetailAnimeBinding
import com.karsatech.karsanime.features.WebViewActivity
import com.karsatech.karsanime.features.WebViewActivity.Companion.WEBVIEW_URL
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullDetailAnimeActivity : AppCompatActivity() {

    private val fullDetailAnimeViewModel: FullDetailAnimeViewModel by viewModels()
    private lateinit var binding: ActivityFullDetailAnimeBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var openingThemeAdapter: ThemeAdapter
    private lateinit var endingThemeAdapter: ThemeAdapter
    private lateinit var externalAdapter: ExternalAdapter
    private lateinit var streamingAdapter: ExternalAdapter
    private lateinit var recommendationAdapter: RecommendationAnimeAdapter

    private var isFavorite = false
    private var isMoreInfoOpen = true
    private var isTrailerOpen = true
    private var isThemeOpen = true
    private var isExternalOpen = true
    private var isStreamingOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getStringExtra(ANIME_ID)

        lifecycle.addObserver(binding.youtubePlayer)

        initializeRecyclerViews()
        setOnClick()
        setToggle()
        observeViewModel(animeId.toString())
    }

    private fun initializeRecyclerViews() {
        binding.rvGenre.apply {
            layoutManager = FlexboxLayoutManager(this@FullDetailAnimeActivity)
            genreAdapter = GenreAdapter()
            adapter = genreAdapter
        }
        binding.rvOpenings.apply {
            layoutManager = LinearLayoutManager(this@FullDetailAnimeActivity, LinearLayoutManager.VERTICAL, false)
            openingThemeAdapter = ThemeAdapter()
            adapter = openingThemeAdapter
        }
        binding.rvEndings.apply {
            layoutManager = LinearLayoutManager(this@FullDetailAnimeActivity, LinearLayoutManager.VERTICAL, false)
            endingThemeAdapter = ThemeAdapter()
            adapter = endingThemeAdapter
        }
        binding.rvExternal.apply {
            layoutManager = LinearLayoutManager(this@FullDetailAnimeActivity, LinearLayoutManager.VERTICAL, false)
            externalAdapter = ExternalAdapter()
            adapter = externalAdapter
        }
        binding.rvStreaming.apply {
            layoutManager = LinearLayoutManager(this@FullDetailAnimeActivity, LinearLayoutManager.VERTICAL, false)
            streamingAdapter = ExternalAdapter()
            adapter = streamingAdapter
        }
        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(this@FullDetailAnimeActivity, LinearLayoutManager.HORIZONTAL, false)
            recommendationAdapter = RecommendationAnimeAdapter()
            adapter = recommendationAdapter
        }
    }

    private fun setToggle() {
        binding.toggleMoreInfo.setOnClickListener {
            isMoreInfoOpen = if (isMoreInfoOpen) {
                binding.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_up)
                binding.layoutTextInfo.visibility = View.GONE
                binding.layoutInfo.visibility = View.GONE
                false
            } else {
                binding.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_down)
                binding.layoutTextInfo.visibility = View.VISIBLE
                binding.layoutInfo.visibility = View.VISIBLE
                true
            }
        }

        binding.toggleTrailer.setOnClickListener {
            isTrailerOpen = if (isTrailerOpen) {
                binding.toggleTrailer.setImageResource(R.drawable.ic_arrow_up)
                binding.youtubePlayer.visibility = View.GONE
                false
            } else {
                binding.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_down)
                binding.youtubePlayer.visibility = View.VISIBLE
                true
            }
        }

        binding.toggleTheme.setOnClickListener {
            isThemeOpen = if (isThemeOpen) {
                binding.toggleTheme.setImageResource(R.drawable.ic_arrow_up)
                binding.textThemeOpenings.visibility = View.GONE
                binding.textThemeEndings.visibility = View.GONE
                binding.rvOpenings.visibility = View.GONE
                binding.rvEndings.visibility = View.GONE
                false
            } else {
                binding.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_down)
                binding.textThemeOpenings.visibility = View.VISIBLE
                binding.textThemeEndings.visibility = View.VISIBLE
                binding.rvOpenings.visibility = View.VISIBLE
                binding.rvEndings.visibility = View.VISIBLE
                true
            }
        }

        binding.toggleExternal.setOnClickListener {
            isExternalOpen = if (isExternalOpen) {
                binding.toggleExternal.setImageResource(R.drawable.ic_arrow_up)
                binding.rvExternal.visibility = View.GONE
                false
            } else {
                binding.toggleExternal.setImageResource(R.drawable.ic_arrow_down)
                binding.rvExternal.visibility = View.VISIBLE
                true
            }
        }

        binding.toggleStreaming.setOnClickListener {
            isStreamingOpen = if (isStreamingOpen) {
                binding.toggleStreaming.setImageResource(R.drawable.ic_arrow_up)
                binding.rvStreaming.visibility = View.GONE
                false
            } else {
                binding.toggleStreaming.setImageResource(R.drawable.ic_arrow_down)
                binding.rvStreaming.visibility = View.VISIBLE
                true
            }
        }
    }

    private fun setRecyclerViewData(data: DetailAnimeItem) {
        genreAdapter.submitList(data.genres)
        openingThemeAdapter.submitList(data.theme?.openings)
        endingThemeAdapter.submitList(data.theme?.endings)
        externalAdapter.submitList(data.external)
        streamingAdapter.submitList(data.streaming)

        genreAdapter.setOnItemClickCallback(object : GenreAdapter.ActionAdapter {
            override fun onItemClick(data: AnimeItem) {
                val intent = Intent(this@FullDetailAnimeActivity, WebViewActivity::class.java)
                intent.putExtra(WEBVIEW_URL, data.url)
                startActivity(intent)
            }
        })

        externalAdapter.setOnItemClickCallback(object : ExternalAdapter.ActionAdapter {
            override fun onItemClick(data: ExternalItem) {
                val intent = Intent(this@FullDetailAnimeActivity, WebViewActivity::class.java)
                intent.putExtra(WEBVIEW_URL, data.url)
                startActivity(intent)
            }
        })

        streamingAdapter.setOnItemClickCallback(object : ExternalAdapter.ActionAdapter {
            override fun onItemClick(data: ExternalItem) {
                val intent = Intent(this@FullDetailAnimeActivity, WebViewActivity::class.java)
                intent.putExtra(WEBVIEW_URL, data.url)
                startActivity(intent)
            }
        })
    }

    private fun setRecommendationsData(data: List<RecommendationItem?>) {
        recommendationAdapter.submitList(data)

        recommendationAdapter.setOnItemClickCallback(object : RecommendationAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: RecommendationItem) {

            }
        })


    }

    private fun observeViewModel(id: String) {
        fullDetailAnimeViewModel.getFullDetailAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> Log.d("FullDetailAnime", "Loading")

                is Resource.Success -> {
                    setFullDataAnime(anime.data?.data!!)
                    setRecyclerViewData(anime.data.data)
                }

                is Resource.Error -> {
                }
            }
        }

        fullDetailAnimeViewModel.getRecommendationAnime(id).observe(this) { recommendation ->
            when (recommendation) {
                is Resource.Loading -> Log.d("getRecommendationAnime", "Loading")

                is Resource.Success -> {
                    Log.d("getRecommendationAnime", recommendation.data?.data?.size.toString())
                    setRecommendationsData(recommendation.data?.data!!)
                }

                is Resource.Error -> {
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
        binding.score.text = "${data.score} (${data.scoredBy})"
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
        binding.infoAiring.text = data.aired?.string ?: ""
        binding.infoAiringFrom.text = data.aired?.from?.withDateFormat()
        binding.infoAiringTo.text = data.aired?.to?.withDateFormat()
        binding.infoDuration.text = data.duration ?: ""
        binding.infoBroadcast.text = data.broadcast?.string ?: ""

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(data.trailer?.youtubeId.toString(), 0f)
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