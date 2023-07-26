package com.karsatech.karsanime.features.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityDetailCharacterBinding
import com.karsatech.karsanime.features.image.ImageActivity

class DetailCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCharacterBinding
    private lateinit var data: People

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<People>(DETAIL_CHARACTER) as People

        setupData(data)

    }

    private fun setupData(data: People) {
        binding.name.text = if (data.name == "null") "n/a" else data.name
        binding.birthday.text = if (data.birthday == "null") "n/a" else data.birthday.withDateFormat()
        binding.favorite.text = if (data.favorites == "null") "0" else data.favorites
        binding.about.text = data.about

        Glide.with(this)
            .load(data.image)
            .into(binding.imagePoster)

        binding.imageCardview.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(ImageActivity.DETAIL_IMAGE, data.image)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val DETAIL_CHARACTER = "DETAIL_CHARACTER"
    }
}