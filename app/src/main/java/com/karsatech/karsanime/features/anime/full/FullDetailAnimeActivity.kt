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
import com.karsatech.karsanime.MainActivity
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
import com.karsatech.karsanime.core.utils.UiUtils.formatWithThousandsSeparator
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityFullDetailAnimeBinding
import com.karsatech.karsanime.features.WebViewActivity
import com.karsatech.karsanime.features.WebViewActivity.Companion.WEBVIEW_URL
import com.karsatech.karsanime.features.anime.character.CharacterActivity
import com.karsatech.karsanime.features.anime.character.CharacterActivity.Companion.CHARACTER_ANIME_ID
import com.karsatech.karsanime.features.anime.character.CharacterActivity.Companion.CHARACTER_ANIME_TITLE
import com.karsatech.karsanime.features.anime.episodes.EpisodesActivity
import com.karsatech.karsanime.features.anime.episodes.EpisodesActivity.Companion.EPISODES_ANIME_ID
import com.karsatech.karsanime.features.anime.episodes.EpisodesActivity.Companion.EPISODES_ANIME_TITLE
import com.karsatech.karsanime.features.anime.news.NewsActivity
import com.karsatech.karsanime.features.anime.news.NewsActivity.Companion.NEWS_ANIME_ID
import com.karsatech.karsanime.features.anime.pictures.PicturesActivity
import com.karsatech.karsanime.features.anime.pictures.PicturesActivity.Companion.PICTURES_ANIME_ID
import com.karsatech.karsanime.features.anime.review.ReviewActivity
import com.karsatech.karsanime.features.anime.review.ReviewActivity.Companion.REVIEW_ANIME_ID
import com.karsatech.karsanime.features.anime.statistic.StatisticActivity
import com.karsatech.karsanime.features.anime.statistic.StatisticActivity.Companion.STATISTIC_ANIME_ID
import com.karsatech.karsanime.features.anime.statistic.StatisticActivity.Companion.STATISTIC_ANIME_TITLE
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
    private var isTrailerOpen = false
    private var isThemeOpen = false
    private var isExternalOpen = false
    private var isStreamingOpen = false
    private var isSynopsisOpen = true
    private var isRecommendationOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getStringExtra(ANIME_ID)

        lifecycle.addObserver(binding.midView.youtubePlayer)

        initializeRecyclerViews()
        setToggle()
        setOnClick()
        observeViewModel(animeId.toString())
    }

    private fun initializeRecyclerViews() {
        binding.topView.rvGenre.apply {
            layoutManager = FlexboxLayoutManager(this@FullDetailAnimeActivity)
            genreAdapter = GenreAdapter()
            adapter = genreAdapter
        }
        binding.midView.rvOpenings.apply {
            layoutManager = LinearLayoutManager(
                this@FullDetailAnimeActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            openingThemeAdapter = ThemeAdapter()
            adapter = openingThemeAdapter
        }
        binding.midView.rvEndings.apply {
            layoutManager = LinearLayoutManager(
                this@FullDetailAnimeActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            endingThemeAdapter = ThemeAdapter()
            adapter = endingThemeAdapter
        }
        binding.midView.rvExternal.apply {
            layoutManager = LinearLayoutManager(
                this@FullDetailAnimeActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            externalAdapter = ExternalAdapter()
            adapter = externalAdapter
        }
        binding.midView.rvStreaming.apply {
            layoutManager = LinearLayoutManager(
                this@FullDetailAnimeActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            streamingAdapter = ExternalAdapter()
            adapter = streamingAdapter
        }
        binding.midView.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(
                this@FullDetailAnimeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recommendationAdapter = RecommendationAnimeAdapter()
            adapter = recommendationAdapter
        }
    }

    private fun setToggle() {

        binding.topView.toggleSynopsis.setOnClickListener {
            isSynopsisOpen = if (isSynopsisOpen) {
                binding.topView.toggleSynopsis.setImageResource(R.drawable.ic_arrow_up)
                binding.topView.synopsis.visibility = View.GONE
                false
            } else {
                binding.topView.toggleSynopsis.setImageResource(R.drawable.ic_arrow_down)
                binding.topView.synopsis.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleMoreInfo.setOnClickListener {
            isMoreInfoOpen = if (isMoreInfoOpen) {
                binding.midView.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.layoutTextInfo.visibility = View.GONE
                binding.midView.layoutInfo.visibility = View.GONE
                false
            } else {
                binding.midView.toggleMoreInfo.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.layoutTextInfo.visibility = View.VISIBLE
                binding.midView.layoutInfo.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleTrailer.setOnClickListener {
            isTrailerOpen = if (isTrailerOpen) {
                binding.midView.toggleTrailer.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.youtubePlayer.visibility = View.GONE
                false
            } else {
                binding.midView.toggleTrailer.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.youtubePlayer.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleTheme.setOnClickListener {
            isThemeOpen = if (isThemeOpen) {
                binding.midView.toggleTheme.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.textThemeOpenings.visibility = View.GONE
                binding.midView.textThemeEndings.visibility = View.GONE
                binding.midView.rvOpenings.visibility = View.GONE
                binding.midView.rvEndings.visibility = View.GONE
                false
            } else {
                binding.midView.toggleTheme.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.textThemeOpenings.visibility = View.VISIBLE
                binding.midView.textThemeEndings.visibility = View.VISIBLE
                binding.midView.rvOpenings.visibility = View.VISIBLE
                binding.midView.rvEndings.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleExternal.setOnClickListener {
            isExternalOpen = if (isExternalOpen) {
                binding.midView.toggleExternal.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.rvExternal.visibility = View.GONE
                false
            } else {
                binding.midView.toggleExternal.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.rvExternal.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleStreaming.setOnClickListener {
            isStreamingOpen = if (isStreamingOpen) {
                binding.midView.toggleStreaming.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.rvStreaming.visibility = View.GONE
                false
            } else {
                binding.midView.toggleStreaming.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.rvStreaming.visibility = View.VISIBLE
                true
            }
        }

        binding.midView.toggleRecommendation.setOnClickListener {
            isRecommendationOpen = if (isRecommendationOpen) {
                binding.midView.toggleRecommendation.setImageResource(R.drawable.ic_arrow_up)
                binding.midView.rvRecommendation.visibility = View.GONE
                false
            } else {
                binding.midView.toggleRecommendation.setImageResource(R.drawable.ic_arrow_down)
                binding.midView.rvRecommendation.visibility = View.VISIBLE
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

        recommendationAdapter.setOnItemClickCallback(object :
            RecommendationAnimeAdapter.ActionAdapter {
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
            .into(binding.topView.imageLandscape)

        Glide.with(this)
            .load(data.images?.jpg?.largeImageUrl)
            .placeholder(R.drawable.error)
            .error(R.drawable.error)
            .dontAnimate()
            .into(binding.topView.imagePotrait)

        binding.topView.title.text = data.title ?: ""
        binding.topView.rating.text = data.rating ?: ""
        binding.topView.type.text = data.type ?: ""
        binding.topView.year.text = if (data.year != null) data.year.toString() else ""
        binding.topView.season.text = data.season ?: ""
        binding.topView.score.text = "${data.score} (${data.scoredBy})"
        binding.topView.textRanking.text =
            if (data.rank != null) "#${data.rank.formatWithThousandsSeparator()}" else "n/a"
        binding.topView.textPopularity.text =
            if (data.popularity != null) "#${data.popularity.formatWithThousandsSeparator()}" else "n/a"
        binding.topView.textMember.text =
            if (data.members != null) data.members.formatWithThousandsSeparator() else "n/a"
        binding.topView.textFavorites.text =
            if (data.favorites != null) data.favorites.formatWithThousandsSeparator() else "n/a"
        binding.topView.synopsis.text = data.synopsis ?: ""
        binding.midView.infoTitle.text = data.title ?: ""
        binding.midView.infoTitleEnglish.text = data.titleEnglish ?: ""
        binding.midView.infoTitleJapanese.text = data.titleJapanese ?: ""
        binding.midView.infoSource.text = data.source ?: ""
        binding.midView.infoEpisodes.text =
            if (data.episodes != null) data.episodes.toString() else ""
        binding.midView.infoAiring.text = data.aired?.string ?: ""
        binding.midView.infoAiringFrom.text = data.aired?.from?.withDateFormat()
        binding.midView.infoAiringTo.text = data.aired?.to?.withDateFormat()
        binding.midView.infoDuration.text = data.duration ?: ""
        binding.midView.infoBroadcast.text = data.broadcast?.string ?: ""

        binding.midView.youtubePlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(data.trailer?.youtubeId.toString(), 0f)
            }
        })

        fullDetailAnimeViewModel.getFavoriteByMalId(data.malId.toString())
            .observe(this) { listAnime ->
                isFavorite = if (listAnime.isEmpty()) {
                    binding.topView.imageFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                    false
                } else {
                    binding.topView.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    true
                }
            }

        val anime = DataMapper.apiResponseToAnimeModel(data)
        binding.topView.btnFavorite.setOnClickListener {
            if (isFavorite) {
                fullDetailAnimeViewModel.setUnFavorite(data.malId.toString())
            } else {
                fullDetailAnimeViewModel.setFavorite(anime)
            }
        }

        binding.btnStatistic.setOnClickListener {
            val intent = Intent(this, StatisticActivity::class.java)
            intent.putExtra(STATISTIC_ANIME_ID, data.malId.toString())
            intent.putExtra(STATISTIC_ANIME_TITLE, data.title)
            startActivity(intent)
        }

        binding.btnCharacter.setOnClickListener {
            val intent = Intent(this, CharacterActivity::class.java)
            intent.putExtra(CHARACTER_ANIME_ID, data.malId.toString())
            intent.putExtra(CHARACTER_ANIME_TITLE, data.title)
            startActivity(intent)
        }

        binding.btnEpisodes.setOnClickListener {
            val intent = Intent(this, EpisodesActivity::class.java)
            intent.putExtra(EPISODES_ANIME_ID, data.malId.toString())
            intent.putExtra(EPISODES_ANIME_TITLE, data.title)
            startActivity(intent)
        }

        binding.btnNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(NEWS_ANIME_ID, data.malId.toString())
            startActivity(intent)
        }

        binding.btnReviews.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra(REVIEW_ANIME_ID, data.malId.toString())
            startActivity(intent)
        }

        binding.btnPictures.setOnClickListener {
            val intent = Intent(this, PicturesActivity::class.java)
            intent.putExtra(PICTURES_ANIME_ID, data.malId.toString())
            startActivity(intent)
        }
    }

    private fun setOnClick() {
        binding.topView.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val ANIME_ID = "ANIME_ID"
    }
}