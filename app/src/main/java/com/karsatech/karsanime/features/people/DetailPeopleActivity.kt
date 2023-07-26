package com.karsatech.karsanime.features.people

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityDetailPeopleBinding
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeopleActivity : AppCompatActivity() {

    private val detailPeopleViewModel: DetailPeopleViewModel by viewModels()
    private lateinit var binding: ActivityDetailPeopleBinding
    private lateinit var data: People
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<People>(DETAIL_PEOPLE) as People

        setupData(data)

    }

    private fun setupData(data: People) {
        binding.name.text = if (data.name == "null") "n/a" else data.name
        binding.birthday.text = if (data.birthday == "null") "n/a" else data.birthday.withDateFormat()
        binding.favorite.text = if (data.favorites == "null") "0" else data.favorites
        binding.about.text = data.about

        Glide.with(this)
            .load(data.image)
            .into(binding.civImagePeople)

        binding.civImagePeople.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(DETAIL_IMAGE, data.image)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                detailPeopleViewModel.setUnFavoritePeople(data.peopleId)
            } else {
                detailPeopleViewModel.setFavoritePeople(data)
            }
        }

        detailPeopleViewModel.getFavoritePeopleByMalId(data.peopleId).observe(this) { listPeople ->
            isFavorite = if (listPeople.isEmpty()) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }

    }

    companion object {
        const val DETAIL_PEOPLE = "DETAIL_PEOPLE"
    }
}