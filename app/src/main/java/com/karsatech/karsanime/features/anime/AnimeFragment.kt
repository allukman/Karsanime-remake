package com.karsatech.karsanime.features.anime

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
import com.karsatech.karsanime.core.ui.AnimeAdapter
import com.karsatech.karsanime.core.ui.ListAnimeAdapter
import com.karsatech.karsanime.core.ui.PeopleAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.core.utils.DataType
import com.karsatech.karsanime.databinding.FragmentAnimeBinding
import com.karsatech.karsanime.features.pagination.ListPaginationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeFragment : Fragment() {

    private val animeViewModel: AnimeViewModel by viewModels()
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var animeUpcomingAdapter: AnimeAdapter
    private lateinit var topAnimeAdapter: AnimeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerViews()
        initOnClick()

        observeViewModelUpcoming()
        observeViewModelTopAnime()
    }

    private fun observeViewModelUpcoming() {
        animeViewModel.upcomingAnime.observe(viewLifecycleOwner) { upcoming ->
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
    }

    private fun observeViewModelTopAnime() {
        animeViewModel.topAnime.observe(viewLifecycleOwner) { anime ->
            when (anime) {
                is Resource.Loading -> showLoadingState(binding.progressBarTopAnime)

                is Resource.Success -> {
                    hideLoadingState(binding.progressBarTopAnime)
                    anime.data?.data?.let { setTopAnimeData(it) }
                }

                is Resource.Error -> {
                    hideLoadingState(binding.progressBarTopAnime)
                    showErrorState(binding.errorTopAnime, anime.message ?: getString(R.string.something_wrong))
                }
            }
        }
    }


    private fun setUpcomingData(data: List<DetailAnimeItem?>) {
        animeUpcomingAdapter.submitList(data)

        animeUpcomingAdapter.setOnItemClickCallback(object : AnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                val anime = DataMapper.apiResponseToAnimeModel(data)
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DetailAnimeActivity.DETAIL_ANIME, anime)
                startActivity(intent)
            }
        })
    }

    private fun setTopAnimeData(data: List<DetailAnimeItem?>) {
        topAnimeAdapter.submitList(data)

        topAnimeAdapter.setOnItemClickCallback(object: AnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                val anime = DataMapper.apiResponseToAnimeModel(data)
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DetailAnimeActivity.DETAIL_ANIME, anime)
                startActivity(intent)
            }

        })
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

    private fun initOnClick() {
        binding.upcomingSeeAll.setOnClickListener {
            val intent = Intent(activity, ListPaginationActivity::class.java)
            intent.putExtra(ListPaginationActivity.SEE_ALL_PAGINATION, DataType.UPCOMING_ANIME)
            startActivity(intent)
        }

        binding.topAnimeSeeAll.setOnClickListener {
            val intent = Intent(activity, ListPaginationActivity::class.java)
            intent.putExtra(ListPaginationActivity.SEE_ALL_PAGINATION, DataType.TOP_ANIME)
            startActivity(intent)
        }
    }

    private fun initializeRecyclerViews() {
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            animeUpcomingAdapter = AnimeAdapter()
            adapter = animeUpcomingAdapter
        }

        binding.rvTopAnime.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            topAnimeAdapter = AnimeAdapter()
            adapter = topAnimeAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}