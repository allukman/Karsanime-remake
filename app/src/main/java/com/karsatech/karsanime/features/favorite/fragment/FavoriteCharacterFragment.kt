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
import com.karsatech.karsanime.core.data.source.local.adapter.FavoriteCharacterAdapter
import com.karsatech.karsanime.core.data.source.local.adapter.FavoritePeopleAdapter
import com.karsatech.karsanime.core.domain.model.Character
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.DataMapper
import com.karsatech.karsanime.databinding.FragmentFavoriteCharacterBinding
import com.karsatech.karsanime.features.character.DetailCharacterActivity
import com.karsatech.karsanime.features.character.DetailCharacterActivity.Companion.DETAIL_CHARACTER
import com.karsatech.karsanime.features.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCharacterFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteCharacterAdapter: FavoriteCharacterAdapter
    private var _binding: FragmentFavoriteCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()

        favoriteViewModel.favoriteCharacter.observe(viewLifecycleOwner) { favoriteCharacter ->
            if (favoriteCharacter.isEmpty()) {
                showEmpty(true)
            } else {
                showEmpty(false)
                setFavoriteCharacterData(favoriteCharacter)
            }
        }
    }

    private fun showEmpty(show: Boolean) {
        binding.lottieEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvEmpty.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvFavoriteCharacter.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun setFavoriteCharacterData(data: List<Character>) {
        favoriteCharacterAdapter.submitList(data)

        favoriteCharacterAdapter.setOnItemClickCallback(object : FavoriteCharacterAdapter.ActionAdapter {
            override fun onItemClick(data: Character) {
                val intent = Intent(activity, DetailCharacterActivity::class.java)
                intent.putExtra(DETAIL_CHARACTER, data)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvFavoriteCharacter.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            favoriteCharacterAdapter = FavoriteCharacterAdapter()
            adapter = favoriteCharacterAdapter
        }
    }

}