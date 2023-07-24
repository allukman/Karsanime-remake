package com.karsatech.karsanime.features.people

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.domain.model.Anime
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityDetailPeopleBinding
import com.karsatech.karsanime.features.anime.DetailAnimeViewModel
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeopleActivity : AppCompatActivity() {

    private val detailPeopleViewModel: DetailPeopleViewModel by viewModels()
    private lateinit var binding: ActivityDetailPeopleBinding
    private lateinit var data: DetailPeopleResponse
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<DetailPeopleResponse>(DETAIL_PEOPLE) as DetailPeopleResponse

        setupData(data)

    }

    private fun setupData(data: DetailPeopleResponse) {
        binding.name.text = data.name
        binding.birthday.text = data.birthday?.withDateFormat()
        binding.favorite.text = data.favorites.toString()
        binding.about.text = data.about

        Glide.with(this)
            .load(data.images!!.jpg!!.imageUrl)
            .into(binding.civImagePeople)

        binding.civImagePeople.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(DETAIL_IMAGE, data.images.jpg!!.imageUrl)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnFavorite.setOnClickListener {
            val favPeople = People(
                peopleId = data.malId.toString(),
                name = data.name.toString(),
                image = data.images.jpg?.imageUrl.toString(),
                favorites = data.favorites.toString()
            )
            if (isFavorite) {
                detailPeopleViewModel.setUnFavoritePeople(data.malId.toString())
            } else {
                detailPeopleViewModel.setFavoritePeople(favPeople)
            }
        }

        detailPeopleViewModel.getFavoritePeopleByMalId(data.malId.toString()).observe(this) { listPeople ->
            isFavorite = if (listPeople.isEmpty()) {
                Log.d("DetailAnimeActivity", "Not Favorite")
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                Log.d("DetailAnimeActivity", "Favorite")
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }

    }

    companion object {
        const val DETAIL_PEOPLE = "DETAIL_PEOPLE"
    }
}