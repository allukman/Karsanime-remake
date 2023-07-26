package com.karsatech.karsanime.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleItem
import com.karsatech.karsanime.core.ui.AnimeAdapter
import com.karsatech.karsanime.core.ui.PeopleAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.DataType
import com.karsatech.karsanime.databinding.FragmentHomeBinding
import com.karsatech.karsanime.features.anime.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.DetailAnimeActivity.Companion.DETAIL_ANIME
import com.karsatech.karsanime.features.character.DetailCharacterActivity
import com.karsatech.karsanime.features.character.DetailCharacterActivity.Companion.DETAIL_CHARACTER
import com.karsatech.karsanime.features.pagination.ListPaginationActivity
import com.karsatech.karsanime.features.pagination.ListPaginationActivity.Companion.SEE_ALL_PAGINATION
import com.karsatech.karsanime.features.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var animeThisSeasonAdapter: AnimeAdapter
    private lateinit var topCharacterAdapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerViews()
        initOnClick()

        observeViewModelAnime()
        observeViewModelCharacters()
    }

    private fun initOnClick() {
        binding.thisSeasonSeeAll.setOnClickListener {
            val intent = Intent(activity, ListPaginationActivity::class.java)
            intent.putExtra(SEE_ALL_PAGINATION, DataType.ANIME_THIS_SEASON)
            startActivity(intent)
        }

        binding.topCharacterSeeAll.setOnClickListener {
            val intent = Intent(activity, ListPaginationActivity::class.java)
            intent.putExtra(SEE_ALL_PAGINATION, DataType.TOP_CHARACTER)
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeRecyclerViews() {
        binding.rvThisSeason.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            animeThisSeasonAdapter = AnimeAdapter()
            adapter = animeThisSeasonAdapter
        }

        binding.rvTopCharacter.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            topCharacterAdapter = PeopleAdapter()
            adapter = topCharacterAdapter
        }
    }

    private fun observeViewModelAnime() {
        homeViewModel.animeThisSeason.observe(viewLifecycleOwner) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressBarThisSeason)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarThisSeason)
                    anime.data?.data?.let { setAnimeThisSeasonData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarThisSeason)
                    showErrorState(binding.errorThisSeason, anime.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }

    private fun observeViewModelCharacters() {
        homeViewModel.topCharacters.observe(viewLifecycleOwner) { characters ->
            when (characters) {
                is Resource.Loading -> showLoadingState(binding.progressBarTopCharacter)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarTopCharacter)
                    characters.data?.data?.let { setTopCharactersData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarTopCharacter)
                    showErrorState(binding.errorTopCharacter, characters.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }


    private fun showLoadingState(progressBar: ShimmerFrameLayout) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState(progressBar: ShimmerFrameLayout) {
        progressBar.visibility = View.GONE
    }

    private fun showErrorState(errorTextView: TextView, errorMessage: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
    }

    private fun setAnimeThisSeasonData(data: List<DetailAnimeItem?>) {
        animeThisSeasonAdapter.submitList(data)

        animeThisSeasonAdapter.setOnItemClickCallback(object : AnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                val anime = DataMapper.apiResponseToAnimeModel(data)
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DETAIL_ANIME, anime)
                startActivity(intent)
            }
        })
    }

    private fun setTopCharactersData(data: List<DetailPeopleItem?>) {
        topCharacterAdapter.submitList(data)

        topCharacterAdapter.setOnItemClickCallback(object : PeopleAdapter.ActionAdapter {
            override fun onItemClick(data: DetailPeopleItem) {
                val character = DataMapper.apiResponseToCharacterModel(data)
                val intent = Intent(activity, DetailCharacterActivity::class.java)
                intent.putExtra(DETAIL_CHARACTER, character)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}