package com.karsatech.karsanime.ui.home

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
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.ui.PeopleAdapter
import com.karsatech.karsanime.core.ui.UpcomingAdapter
import com.karsatech.karsanime.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var topAnimeAdapter: UpcomingAdapter
    private lateinit var topMangaAdapter: UpcomingAdapter
    private lateinit var topPeopleAdapter: PeopleAdapter

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
        observeViewModelData()
    }

    private fun initializeRecyclerViews() {
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            upcomingAdapter = UpcomingAdapter()
            adapter = upcomingAdapter
        }

        binding.rvTopAnime.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            topAnimeAdapter = UpcomingAdapter()
            adapter = topAnimeAdapter
        }

        binding.rvTopManga.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            topMangaAdapter = UpcomingAdapter()
            adapter = topMangaAdapter
        }

        binding.rvTopPeople.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            topPeopleAdapter = PeopleAdapter()
            adapter = topPeopleAdapter
        }
    }

    private fun observeViewModelData() {
        homeViewModel.upcomingAnime.observe(viewLifecycleOwner) { upcoming ->
            when (upcoming) {
                is Resource.Loading -> showLoadingState(binding.progressBarUpcoming)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarUpcoming)
                    upcoming.data?.data?.let { setUpcomingData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarUpcoming)
                    showErrorState(binding.errorUpcoming, upcoming.message ?: getString(R.string.something_wrong))
                }
            }
        }

        homeViewModel.topAnime.observe(viewLifecycleOwner) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressBarAnime)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarAnime)
                    anime.data?.data?.let { setTopAnimeData(it) }
                    observeViewModelMangaData()
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarAnime)
                    showErrorState(binding.errorTopAnime, anime.message ?: getString(R.string.something_wrong))
                    observeViewModelMangaData()
                }
            }
        }

        homeViewModel.topPeople.observe(viewLifecycleOwner) { people ->
            when (people) {
                is Resource.Loading -> showLoadingState(binding.progressBarTopPeople)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarTopPeople)
                    people.data?.data?.let { setTopPeopleData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarTopPeople)
                    showErrorState(binding.errorTopPeople, people.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }

    private fun observeViewModelMangaData() {
        homeViewModel.topManga.observe(viewLifecycleOwner) { manga ->
            when (manga) {
                is Resource.Loading -> showLoadingState(binding.progressBarManga)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarManga)
                    manga.data?.data?.let { setTopMangaData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarManga)
                    showErrorState(binding.errorTopManga, manga.message ?: getString(R.string.something_wrong))
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

    private fun setUpcomingData(data: ArrayList<DetailGeneralResponse?>) {
        upcomingAdapter.submitList(data)
    }

    private fun setTopAnimeData(data: ArrayList<DetailGeneralResponse?>) {
        topAnimeAdapter.submitList(data)
    }

    private fun setTopMangaData(data: ArrayList<DetailGeneralResponse?>) {
        topMangaAdapter.submitList(data)
    }

    private fun setTopPeopleData(data: ArrayList<DetailPeopleResponse?>) {
        topPeopleAdapter.submitList(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}