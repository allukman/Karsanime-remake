package com.karsatech.karsanime.features.favorite.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karsatech.karsanime.R
import com.karsatech.karsanime.databinding.FragmentFavoriteMangaBinding
import com.karsatech.karsanime.databinding.FragmentFavoritePeopleBinding

class FavoritePeopleFragment : Fragment() {
    private var _binding: FragmentFavoritePeopleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePeopleBinding.inflate(inflater, container, false)
        return binding.root
    }
}