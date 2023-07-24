package com.karsatech.karsanime.features.favorite.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.local.adapter.FavoriteMangaAdapter
import com.karsatech.karsanime.core.data.source.local.adapter.FavoritePeopleAdapter
import com.karsatech.karsanime.core.domain.model.Manga
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.databinding.FragmentFavoriteMangaBinding
import com.karsatech.karsanime.databinding.FragmentFavoritePeopleBinding
import com.karsatech.karsanime.features.favorite.FavoriteViewModel
import com.karsatech.karsanime.features.people.DetailPeopleActivity
import com.karsatech.karsanime.features.people.DetailPeopleActivity.Companion.DETAIL_PEOPLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePeopleFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoritePeopleAdapter: FavoritePeopleAdapter

    private var _binding: FragmentFavoritePeopleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViews()

        favoriteViewModel.favoritePeople.observe(viewLifecycleOwner) { favoritePeople ->
            setFavoritePeopleData(favoritePeople)
        }
    }

    private fun setFavoritePeopleData(data: List<People>) {
        favoritePeopleAdapter.submitList(data)

        favoritePeopleAdapter.setOnItemClickCallback(object : FavoritePeopleAdapter.ActionAdapter {
            override fun onItemClick(data: People) {
                val intent = Intent(activity, DetailPeopleActivity::class.java)
                intent.putExtra(DETAIL_PEOPLE, data)
                startActivity(intent)
            }
        })
    }

    private fun initializeRecyclerViews() {
        binding.rvFavoritePeople.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            favoritePeopleAdapter = FavoritePeopleAdapter()
            adapter = favoritePeopleAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}