package com.karsatech.karsanime.features.anime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.paging.LoadingStateAdapter
import com.karsatech.karsanime.core.ui.ListAnimeAdapter
import com.karsatech.karsanime.databinding.FragmentAnimeBinding
import com.karsatech.karsanime.features.anime.DetailAnimeActivity.Companion.DETAIL_ANIME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeFragment : Fragment() {

    private val animeViewModel: AnimeViewModel by viewModels()
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAnimeAdapter: ListAnimeAdapter

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
    }

    private fun storyLoadingState() {
        listAnimeAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.rvListAnime.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.rvListAnime.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                is LoadState.Error -> {

                }
            }
        }
    }

    private fun observeViewModelAnime() {
        animeViewModel.topAnimePagination.observe(viewLifecycleOwner) { anime ->
            listAnimeAdapter.submitData(lifecycle, anime)
            setAnimeData()
        }
    }

    private fun setAnimeData() {
        listAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: DetailGeneralResponse) {
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DETAIL_ANIME, data)
                startActivity(intent)
            }

        })
    }

    private fun initializeRecyclerViews() {
        binding.rvListAnime.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            listAnimeAdapter = ListAnimeAdapter()
            adapter = listAnimeAdapter
        }

        storyLoadingState()
        observeViewModelAnime()

        binding.rvListAnime.adapter = listAnimeAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listAnimeAdapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}