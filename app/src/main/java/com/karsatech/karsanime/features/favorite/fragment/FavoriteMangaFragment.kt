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
import com.karsatech.karsanime.core.data.source.local.adapter.FavoriteMangaAdapter
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.databinding.FragmentFavoriteMangaBinding
import com.karsatech.karsanime.features.favorite.FavoriteViewModel
import com.karsatech.karsanime.features.manga.DetailMangaActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMangaFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteMangaAdapter: FavoriteMangaAdapter

    private var _binding: FragmentFavoriteMangaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()

        favoriteViewModel.favoriteManga.observe(viewLifecycleOwner) { favoriteManga ->
            if (favoriteManga.isEmpty()) {
                showEmpty(true)
            } else {
                showEmpty(false)
                setFavoriteMangaData(favoriteManga)
            }
        }
    }

    private fun showEmpty(show: Boolean) {
        binding.lottieEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvFavoriteManga.visibility = if (show) View.GONE else View.VISIBLE
    }
    private fun setFavoriteMangaData(data: List<Manga>) {
        favoriteMangaAdapter.submitList(data)

        favoriteMangaAdapter.setOnItemClickCallback(object : FavoriteMangaAdapter.ActionAdapter {
            override fun onItemClick(data: Manga) {
                val intent = Intent(activity, DetailMangaActivity::class.java)
                intent.putExtra(DetailMangaActivity.DETAIL_MANGA, data)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvFavoriteManga.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            favoriteMangaAdapter = FavoriteMangaAdapter()
            adapter = favoriteMangaAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}