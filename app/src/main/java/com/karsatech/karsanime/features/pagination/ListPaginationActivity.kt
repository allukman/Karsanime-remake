package com.karsatech.karsanime.features.pagination

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.paging.LoadingStateAdapter
import com.karsatech.karsanime.core.ui.ListAnimeAdapter
import com.karsatech.karsanime.core.ui.ListCharacterAdapter
import com.karsatech.karsanime.core.ui.ListMangaAdapter
import com.karsatech.karsanime.core.ui.ListPeopleAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.DataType
import com.karsatech.karsanime.databinding.ActivityListPaginationBinding
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity.Companion.DETAIL_ANIME
import com.karsatech.karsanime.features.character.DetailCharacterActivity
import com.karsatech.karsanime.features.character.DetailCharacterActivity.Companion.DETAIL_CHARACTER
import com.karsatech.karsanime.features.manga.DetailMangaActivity
import com.karsatech.karsanime.features.people.DetailPeopleActivity
import com.karsatech.karsanime.features.people.DetailPeopleActivity.Companion.DETAIL_PEOPLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPaginationActivity : AppCompatActivity() {

    private val listPaginationViewModel: ListPaginationViewModel by viewModels()
    private lateinit var binding: ActivityListPaginationBinding

    private lateinit var listAnimeAdapter: ListAnimeAdapter
    private lateinit var listMangaAdapter: ListMangaAdapter
    private lateinit var listPeopleAdapter: ListPeopleAdapter
    private lateinit var listCharacterAdapter: ListCharacterAdapter
    private lateinit var listUpcomingAnimeAdapter: ListAnimeAdapter
    private lateinit var listThisSeasonAnimeAdapter: ListAnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPaginationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initializeRecyclerViews()

        val dataType = intent.getSerializableExtra(SEE_ALL_PAGINATION) as? DataType
        dataType?.let { setupList(it) } ?: Log.d("ListPaginationActivity", "Invalid type data")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecyclerViews() {
        binding.rvListAnime.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listAnimeAdapter = ListAnimeAdapter()
            adapter = listAnimeAdapter
        }
        binding.rvListManga.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listMangaAdapter = ListMangaAdapter()
            adapter = listMangaAdapter
        }
        binding.rvListPeople.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listPeopleAdapter = ListPeopleAdapter()
            adapter = listPeopleAdapter
        }
        binding.rvListUpcomingAnime.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listUpcomingAnimeAdapter = ListAnimeAdapter()
            adapter = listUpcomingAnimeAdapter
        }
        binding.rvListCharacter.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listCharacterAdapter = ListCharacterAdapter()
            adapter = listCharacterAdapter
        }
        binding.rvListThisSeasonAnime.apply {
            layoutManager = LinearLayoutManager(this@ListPaginationActivity, RecyclerView.VERTICAL, false)
            listThisSeasonAnimeAdapter = ListAnimeAdapter()
            adapter = listThisSeasonAnimeAdapter
        }

    }

    private fun setupList(dataType: DataType) {
        val titleResId = when (dataType) {
            DataType.UPCOMING_ANIME -> {
                R.string.upcoming_anime
            }
            DataType.TOP_ANIME -> {
                R.string.top_anime
            }
            DataType.TOP_MANGA -> {
                R.string.top_manga
            }
            DataType.TOP_PEOPLE -> {
                R.string.top_people
            }
            DataType.ANIME_THIS_SEASON -> {
                R.string.this_season
            }
            DataType.TOP_CHARACTER -> {
                R.string.top_character
            }
        }
        supportActionBar?.title = getString(titleResId)

        when (dataType) {
            DataType.UPCOMING_ANIME -> {
                initUpcomingAnimeData()
            }
            DataType.TOP_ANIME -> {
                initAnimeData()
            }
            DataType.TOP_MANGA -> {
                initMangaData()
            }
            DataType.TOP_PEOPLE -> {
                initPeopleData()
            }
            DataType.ANIME_THIS_SEASON -> {
                initThisSeasonAnimeData()
            }
            DataType.TOP_CHARACTER -> {
                initCharacterData()
            }
        }
    }

    // Initialize RecyclerView data and loading state for each data type
    private fun initAnimeData() {
        listAnimeLoadingState()
        observeViewModelAnime()
        binding.layoutTopAnime.visibility = View.VISIBLE
        binding.rvListAnime.adapter = listAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listAnimeAdapter.retry() }
        )
        setAnimeData()
    }

    private fun initMangaData() {
        listMangaLoadingState()
        observeViewModelManga()
        binding.layoutTopManga.visibility = View.VISIBLE
        binding.rvListManga.adapter = listMangaAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listMangaAdapter.retry() }
        )
        setMangaData()
    }

    private fun initPeopleData() {
        listPeopleLoadingState()
        observeViewModelPeople()
        binding.layoutTopPeople.visibility = View.VISIBLE
        binding.rvListPeople.adapter = listPeopleAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listPeopleAdapter.retry() }
        )
        setPeopleData()
    }

    private fun initUpcomingAnimeData() {
        listUpcomingAnimeLoadingState()
        observeViewModelUpcomingAnime()
        binding.layoutTopUpcomingAnime.visibility = View.VISIBLE
        binding.rvListUpcomingAnime.adapter = listUpcomingAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listUpcomingAnimeAdapter.retry() }
        )
        setUpcomingAnimeData()
    }

    private fun initCharacterData() {
        listCharacterLoadingState()
        observeViewModelCharacter()
        binding.layoutTopCharacter.visibility = View.VISIBLE
        binding.rvListCharacter.adapter = listCharacterAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listCharacterAdapter.retry() }
        )
        setCharacterData()
    }

    private fun initThisSeasonAnimeData() {
        listThisSeasonAnimeLoadingState()
        observeViewModelThisSeasonAnime()
        binding.layoutThisSeasonAnime.visibility = View.VISIBLE
        binding.rvListThisSeasonAnime.adapter = listThisSeasonAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listThisSeasonAnimeAdapter.retry() }
        )
        setThisSeasonAnimeData()
    }

    // Observers
    private fun observeViewModelAnime() {
        listPaginationViewModel.topAnimePagination.observe(this) { anime ->
            listAnimeAdapter.submitData(lifecycle, anime)
        }
    }

    private fun observeViewModelManga() {
        listPaginationViewModel.topMangaPagination.observe(this) { manga ->
            listMangaAdapter.submitData(lifecycle, manga)
        }
    }

    private fun observeViewModelPeople() {
        listPaginationViewModel.topPeoplePagination.observe(this) { people ->
            listPeopleAdapter.submitData(lifecycle, people)
        }
    }

    private fun observeViewModelUpcomingAnime() {
        listPaginationViewModel.upcomingAnimePagination.observe(this) { upcoming ->
            listUpcomingAnimeAdapter.submitData(lifecycle, upcoming)
        }
    }

    private fun observeViewModelCharacter() {
        listPaginationViewModel.topCharacterPagination.observe(this) { character ->
            listCharacterAdapter.submitData(lifecycle, character)
        }
    }

    private fun observeViewModelThisSeasonAnime() {
        listPaginationViewModel.thisSeasonAnimePagination.observe(this) { anime ->
            listThisSeasonAnimeAdapter.submitData(lifecycle, anime)
        }
    }

    // Loading state for each RecyclerView
    private fun listAnimeLoadingState() {
        listAnimeAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListAnime, binding.progressBarTopAnime, it)
        }
    }

    private fun listMangaLoadingState() {
        listMangaAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListManga, binding.progressBarTopManga, it)
        }
    }

    private fun listPeopleLoadingState() {
        listPeopleAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListPeople, binding.progressBarTopPeople, it)
        }
    }

    private fun listUpcomingAnimeLoadingState() {
        listUpcomingAnimeAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListUpcomingAnime, binding.progressBarTopUpcomingAnime, it)
        }
    }

    private fun listCharacterLoadingState() {
        listCharacterAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListCharacter, binding.progressBarTopCharacter, it)
        }
    }

    private fun listThisSeasonAnimeLoadingState() {
        listThisSeasonAnimeAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListThisSeasonAnime, binding.progressBarThisSeasonAnime, it)
        }
    }

    // Common function to handle RecyclerView loading state
    private fun handleLoadingState(
        recyclerView: RecyclerView,
        progressBar: View,
        loadState: CombinedLoadStates
    ) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is LoadState.NotLoading -> {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            is LoadState.Error -> {
                // Handle error state here if needed
            }
        }
    }

    // Set click listeners for each RecyclerView
    private fun setAnimeData() {
        listAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                startDetailActivity(DetailAnimeActivity::class.java, DETAIL_ANIME, data)
            }
        })
    }

    private fun setMangaData() {
        listMangaAdapter.setOnItemClickCallback(object : ListMangaAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                startDetailActivity(DetailMangaActivity::class.java, DetailMangaActivity.DETAIL_MANGA, data)
            }
        })
    }

    private fun setPeopleData() {
        listPeopleAdapter.setOnItemClickCallback(object : ListPeopleAdapter.ActionAdapter {
            override fun onItemClick(data: DetailPeopleItem) {
                val people = DataMapper.apiResponseToPeopleModel(data)
                val intent = Intent(this@ListPaginationActivity, DetailPeopleActivity::class.java)
                intent.putExtra(DETAIL_PEOPLE, people)
                startActivity(intent)
            }
        })
    }

    private fun setUpcomingAnimeData() {
        listUpcomingAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                startDetailActivity(DetailAnimeActivity::class.java, DETAIL_ANIME, data)
            }
        })
    }

    private fun setCharacterData() {
        listCharacterAdapter.setOnItemClickCallback(object : ListCharacterAdapter.ActionAdapter {
            override fun onItemClick(data: DetailPeopleItem) {
                val character = DataMapper.apiResponseToCharacterModel(data)
                val intent = Intent(this@ListPaginationActivity, DetailCharacterActivity::class.java)
                intent.putExtra(DETAIL_CHARACTER, character)
                startActivity(intent)
            }
        })
    }

    private fun setThisSeasonAnimeData() {
        listThisSeasonAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                startDetailActivity(DetailAnimeActivity::class.java, DETAIL_ANIME, data)
            }
        })
    }

    // Start detail activity for each data type
    private fun startDetailActivity(
        targetActivity: Class<*>,
        extraKey: String,
        data: DetailAnimeItem
    ) {
        val intentData = if (extraKey == DETAIL_ANIME) {
            DataMapper.apiResponseToAnimeModel(data)
        } else {
            DataMapper.apiResponseToMangaModel(data)
        }
        val intent = Intent(this@ListPaginationActivity, targetActivity)
        intent.putExtra(extraKey, intentData)
        startActivity(intent)
    }

    companion object {
        const val SEE_ALL_PAGINATION = "SEE_ALL_PAGINATION"
    }
}