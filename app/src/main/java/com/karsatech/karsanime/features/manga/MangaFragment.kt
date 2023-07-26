package com.karsatech.karsanime.features.manga

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.remote.response.anime.DetailAnimeItem
import com.karsatech.karsanime.core.paging.LoadingStateAdapter
import com.karsatech.karsanime.core.ui.ListMangaAdapter
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.databinding.FragmentMangaBinding
import com.karsatech.karsanime.features.manga.DetailMangaActivity.Companion.DETAIL_MANGA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangaFragment : Fragment() {

    private val mangaViewModel: MangaViewModel by viewModels()
    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!

    private lateinit var listMangaAdapter: ListMangaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()
    }

    private fun listMangaLoadingState() {
        listMangaAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.rvListManga.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.rvListManga.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                is LoadState.Error -> {

                }
            }
        }
    }

    private fun observeViewModelManga() {
        mangaViewModel.topMangaPagination.observe(viewLifecycleOwner) { anime ->
            listMangaAdapter.submitData(lifecycle, anime)
            setMangaData()
        }
    }

    private fun setMangaData() {
        listMangaAdapter.setOnItemClickCallback(object : ListMangaAdapter.ActionAdapter {
            override fun onItemClick(data: DetailAnimeItem) {
                val manga = DataMapper.apiResponseToMangaModel(data)
                val intent = Intent(activity, DetailMangaActivity::class.java)
                intent.putExtra(DETAIL_MANGA, manga)
                startActivity(intent)
            }

        })
    }

    private fun initializeRecyclerViews() {
        binding.rvListManga.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            listMangaAdapter = ListMangaAdapter()
            adapter = listMangaAdapter
        }

        listMangaLoadingState()
        observeViewModelManga()

        binding.rvListManga.adapter = listMangaAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { listMangaAdapter.retry() }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}