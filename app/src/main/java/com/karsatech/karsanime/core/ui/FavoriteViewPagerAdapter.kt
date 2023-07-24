package com.karsatech.karsanime.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.karsatech.karsanime.features.favorite.fragment.FavoriteAnimeFragment
import com.karsatech.karsanime.features.favorite.fragment.FavoriteMangaFragment
import com.karsatech.karsanime.features.favorite.fragment.FavoritePeopleFragment

class FavoriteViewPagerAdapter (activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteAnimeFragment()
            1 -> fragment = FavoriteMangaFragment()
            2 -> fragment = FavoritePeopleFragment()
        }
        return fragment as Fragment
    }

    companion object {
        private const val ITEM_COUNT = 3
    }
}