package com.karsatech.karsanime.features.favorite.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.core.data.source.local.adapter.FavoriteAnimeAdapter
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.databinding.FragmentFavoriteAnimeBinding
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity
import com.karsatech.karsanime.features.anime.detail.DetailAnimeActivity.Companion.DETAIL_ANIME
import com.karsatech.karsanime.features.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteAnimeFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAnimeAdapter: FavoriteAnimeAdapter

    private var _binding: FragmentFavoriteAnimeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()

        favoriteViewModel.favoriteAnime.observe(viewLifecycleOwner) { favoriteAnime ->
            if (favoriteAnime.isEmpty()) {
                showEmpty(true)
            } else {
                showEmpty(false)
                setFavoriteAnimeData(favoriteAnime)
            }
        }
    }

    private fun showEmpty(show: Boolean) {
        binding.lottieEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvFavoriteAnime.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun setFavoriteAnimeData(data: List<Anime>) {
        favoriteAnimeAdapter.submitList(data)

        favoriteAnimeAdapter.setOnItemClickCallback(object : FavoriteAnimeAdapter.ActionAdapter {
            override fun onItemClick(data: Anime) {
                val intent = Intent(activity, DetailAnimeActivity::class.java)
                intent.putExtra(DETAIL_ANIME, data)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvFavoriteAnime.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            favoriteAnimeAdapter = FavoriteAnimeAdapter()
            adapter = favoriteAnimeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}