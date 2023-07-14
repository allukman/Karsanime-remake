package com.karsatech.karsanime.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karsatech.karsanime.R
import com.karsatech.karsanime.databinding.FragmentAnimeBinding
import com.karsatech.karsanime.databinding.FragmentHomeBinding

class AnimeFragment : Fragment() {

    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}