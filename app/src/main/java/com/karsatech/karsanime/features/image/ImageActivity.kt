package com.karsatech.karsanime.features.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.karsatech.karsanime.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        val data = intent.getStringExtra(DETAIL_IMAGE)
        Glide.with(applicationContext)
            .load(data)
            .fitCenter()
            .into(binding.ivDetailImage)

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val DETAIL_IMAGE = "DETAIL_IMAGE"
    }
}