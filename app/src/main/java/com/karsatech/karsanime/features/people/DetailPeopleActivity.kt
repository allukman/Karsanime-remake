package com.karsatech.karsanime.features.people

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karsatech.karsanime.core.data.source.remote.response.people.DetailPeopleResponse
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityDetailPeopleBinding
import com.karsatech.karsanime.features.image.ImageActivity
import com.karsatech.karsanime.features.image.ImageActivity.Companion.DETAIL_IMAGE

class DetailPeopleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPeopleBinding
    private lateinit var data: DetailPeopleResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<DetailPeopleResponse>(DETAIL_PEOPLE) as DetailPeopleResponse

        setupData(data)
        Log.d("DetailPeopleActivity", data.toString())

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

    }

    companion object {
        const val DETAIL_PEOPLE = "DETAIL_PEOPLE"
    }
}