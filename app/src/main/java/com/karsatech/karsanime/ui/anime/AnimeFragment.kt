package com.karsatech.karsanime.ui.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailGeneralResponse
import com.karsatech.karsanime.core.ui.ListAnimeAdapter
import com.karsatech.karsanime.databinding.FragmentAnimeBinding
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
        observeViewModelAnime()
    }

    private fun observeViewModelAnime() {
        animeViewModel.topAnime.observe(viewLifecycleOwner) { anime ->
            when (anime) {
                is Resource.Loading -> Log.d("AnimeFragment", "Loading")

                is Resource.Success -> {
                    anime.data?.data?.let { setAnimeData(it) }
                }

                is Resource.Error -> {
                    Log.d("AnimeFragment", "Error")
                }
            }
        }
    }

    private fun setAnimeData(data: ArrayList<DetailGeneralResponse?>) {
        listAnimeAdapter.submitList(data)

    }

    private fun initializeRecyclerViews() {
        binding.rvListAnime.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            listAnimeAdapter = ListAnimeAdapter()
            adapter = listAnimeAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}