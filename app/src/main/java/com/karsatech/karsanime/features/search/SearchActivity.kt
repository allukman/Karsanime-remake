package com.karsatech.karsanime.features.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
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
import com.karsatech.karsanime.core.utils.FilterType
import com.karsatech.karsanime.databinding.ActivitySearchBinding
import com.karsatech.karsanime.features.anime.DetailAnimeActivity
import com.karsatech.karsanime.features.character.DetailCharacterActivity
import com.karsatech.karsanime.features.manga.DetailMangaActivity
import com.karsatech.karsanime.features.people.DetailPeopleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var binding: ActivitySearchBinding
    var searchType = FilterType.ANIME

    private lateinit var listAnimeAdapter: ListAnimeAdapter
    private lateinit var listMangaAdapter: ListMangaAdapter
    private lateinit var listPeopleAdapter: ListPeopleAdapter
    private lateinit var listCharacterAdapter: ListCharacterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Search"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initSearchView()
        initOnClick()
        initializeRecyclerViews()
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

    private fun initSearchView() {
        binding.searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                when (searchType) {
                    FilterType.ANIME -> initAnimeData(query.toString())
                    FilterType.MANGA -> initMangaData(query.toString())
                    FilterType.CHARACTER -> initCharacterData(query.toString())
                    FilterType.PEOPLE -> initPeopleData(query.toString())
                }

                binding.searchButton.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showSortingPopMenu() {

        PopupMenu(this, binding.btnFilter).run {
            menuInflater.inflate(R.menu.option_menu, menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.anime -> {
                        Toast.makeText(this@SearchActivity, "Anime", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.ANIME
                        binding.searchButton.queryHint = "Search in anime"
                        true
                    }

                    R.id.manga -> {
                        Toast.makeText(this@SearchActivity, "Manga", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.MANGA
                        binding.searchButton.queryHint = "Search in manga"
                        true
                    }

                    R.id.character -> {
                        Toast.makeText(this@SearchActivity, "Character", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.CHARACTER
                        binding.searchButton.queryHint = "Search in character"
                        true
                    }

                    R.id.people -> {
                        Toast.makeText(this@SearchActivity, "People", Toast.LENGTH_SHORT).show()
                        searchType = FilterType.PEOPLE
                        binding.searchButton.queryHint = "Search in people"
                        true
                    }

                    else -> false
                }
            }
            show()
        }
    }

    private fun initializeRecyclerViews() {
        binding.rvListAnime.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            listAnimeAdapter = ListAnimeAdapter()
            adapter = listAnimeAdapter
        }
        binding.rvListManga.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            listMangaAdapter = ListMangaAdapter()
            adapter = listMangaAdapter
        }
        binding.rvListPeople.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            listPeopleAdapter = ListPeopleAdapter()
            adapter = listPeopleAdapter
        }
        binding.rvListCharacter.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            listCharacterAdapter = ListCharacterAdapter()
            adapter = listCharacterAdapter
        }
    }

    private fun initAnimeData(query: String) {
        listAnimeLoadingState()
        observeViewModelAnime(query = query)

        binding.layoutTopAnime.visibility = View.VISIBLE
        binding.layoutTopManga.visibility = View.GONE
        binding.layoutTopPeople.visibility = View.GONE
        binding.layoutTopCharacter.visibility = View.GONE

        binding.rvListAnime.adapter = listAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listAnimeAdapter.retry() }
        )

        setAnimeData()
    }

    private fun initMangaData(query: String) {
        listMangaLoadingState()
        observeViewModelManga(query = query)

        binding.layoutTopAnime.visibility = View.GONE
        binding.layoutTopManga.visibility = View.VISIBLE
        binding.layoutTopPeople.visibility = View.GONE
        binding.layoutTopCharacter.visibility = View.GONE

        binding.rvListManga.adapter = listMangaAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listMangaAdapter.retry() }
        )

        setMangaData()
    }

    private fun initPeopleData(query: String) {
        listPeopleLoadingState()
        observeViewModelPeople(query)

        binding.layoutTopAnime.visibility = View.GONE
        binding.layoutTopManga.visibility = View.GONE
        binding.layoutTopPeople.visibility = View.VISIBLE
        binding.layoutTopCharacter.visibility = View.GONE

        binding.rvListPeople.adapter = listPeopleAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listPeopleAdapter.retry() }
        )
        setPeopleData()
    }

    private fun initCharacterData(query: String) {
        listCharacterLoadingState()
        observeViewModelCharacter(query)

        binding.layoutTopAnime.visibility = View.GONE
        binding.layoutTopManga.visibility = View.GONE
        binding.layoutTopPeople.visibility = View.GONE
        binding.layoutTopCharacter.visibility = View.VISIBLE

        binding.rvListCharacter.adapter = listCharacterAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listCharacterAdapter.retry() }
        )
        setCharacterData()
    }
    private fun observeViewModelAnime(query: String) {
        searchViewModel.searchAnime(query).observe(this) { anime ->
            listAnimeAdapter.submitData(lifecycle, anime)
        }
    }

    private fun observeViewModelManga(query: String) {
        searchViewModel.searchManga(query).observe(this) { manga ->
            listMangaAdapter.submitData(lifecycle, manga)
        }
    }

    private fun observeViewModelPeople(query: String) {
        searchViewModel.searchPeople(query).observe(this) { people ->
            listPeopleAdapter.submitData(lifecycle, people)
        }
    }

    private fun observeViewModelCharacter(query: String) {
        searchViewModel.searchCharacter(query).observe(this) { character ->
            listCharacterAdapter.submitData(lifecycle, character)
        }
    }

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

    private fun listCharacterLoadingState() {
        listCharacterAdapter.addLoadStateListener {
            handleLoadingState(binding.rvListCharacter, binding.progressBarTopCharacter, it)
        }
    }

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

    private fun setAnimeData() {
        listAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                startDetailActivity(
                    DetailAnimeActivity::class.java,
                    DetailAnimeActivity.DETAIL_ANIME, data)
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
                val intent = Intent(this@SearchActivity, DetailPeopleActivity::class.java)
                intent.putExtra(DetailPeopleActivity.DETAIL_PEOPLE, people)
                startActivity(intent)
            }
        })
    }

    private fun setCharacterData() {
        listCharacterAdapter.setOnItemClickCallback(object : ListCharacterAdapter.ActionAdapter {
            override fun onItemClick(data: DetailPeopleItem) {
                val character = DataMapper.apiResponseToCharacterModel(data)
                val intent = Intent(this@SearchActivity, DetailCharacterActivity::class.java)
                intent.putExtra(DetailCharacterActivity.DETAIL_CHARACTER, character)
                startActivity(intent)
            }
        })
    }

    private fun startDetailActivity(
        targetActivity: Class<*>,
        extraKey: String,
        data: DetailAnimeItem
    ) {
        val intentData = if (extraKey == DetailAnimeActivity.DETAIL_ANIME) {
            DataMapper.apiResponseToAnimeModel(data)
        } else {
            DataMapper.apiResponseToMangaModel(data)
        }
        val intent = Intent(this, targetActivity)
        intent.putExtra(extraKey, intentData)
        startActivity(intent)
    }

    private fun initOnClick() {
        binding.btnFilter.setOnClickListener {
            showSortingPopMenu()
        }
    }
}