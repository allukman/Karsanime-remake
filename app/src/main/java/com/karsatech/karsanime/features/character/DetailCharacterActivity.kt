package com.karsatech.karsanime.features.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.domain.model.People
import com.karsatech.karsanime.core.domain.model.Character
import com.karsatech.karsanime.core.utils.UiUtils.withDateFormat
import com.karsatech.karsanime.databinding.ActivityDetailCharacterBinding
import com.karsatech.karsanime.features.image.ImageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCharacterActivity : AppCompatActivity() {

    private val detailCharacterViewModel: DetailCharacterViewModel by viewModels()
    private lateinit var binding: ActivityDetailCharacterBinding
    private lateinit var data: Character
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<Character>(DETAIL_CHARACTER) as Character

        setupData(data)

    }

    private fun setupData(data: Character) {
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

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                detailCharacterViewModel.setUnFavoriteCharacter(data.characterId)
            } else {
                detailCharacterViewModel.setFavoriteCharacter(data)
            }
        }

        detailCharacterViewModel.getFavoriteCharacterByMalId(data.characterId).observe(this) { listCharacter ->
            isFavorite = if (listCharacter.isEmpty()) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
                false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }
    }

    companion object {
        const val DETAIL_CHARACTER = "DETAIL_CHARACTER"
    }
}