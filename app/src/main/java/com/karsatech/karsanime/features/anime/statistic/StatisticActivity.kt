package com.karsatech.karsanime.features.anime.statistic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.karsatech.karsanime.R
import com.karsatech.karsanime.core.data.Resource
import com.karsatech.karsanime.core.data.source.remote.response.anime.StatisticItem
import com.karsatech.karsanime.core.utils.UiUtils.formatWithThousandsSeparator
import com.karsatech.karsanime.databinding.ActivityStatisticBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticActivity : AppCompatActivity() {

    private val statisticViewModel: StatisticViewModel by viewModels()
    private lateinit var binding: ActivityStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Anime Statistic"

        val animeId = intent.getStringExtra(STATISTIC_ANIME_ID)
        val animeTitle = intent.getStringExtra(STATISTIC_ANIME_TITLE)

        binding.title.text = animeTitle

        observeViewModel(animeId.toString())
    }

    private fun observeViewModel(id: String) {
        statisticViewModel.getStatisticAnime(id).observe(this) { anime ->
            when (anime) {
                is Resource.Loading -> Log.d("StatisticActivity", "Loading")

                is Resource.Success -> {
                    Log.d("StatisticActivity", anime.data?.data.toString())
                    setupPieChart(anime.data?.data)
                }

                is Resource.Error -> {
                    Log.d("StatisticActivity", "Error")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupPieChart(data: StatisticItem?) {

        val watching = data?.watching ?: 0
        val completed = data?.completed ?: 0
        val onHold = data?.onHold ?: 0
        val dropped = data?.dropped ?: 0
        val planToWatch = data?.planToWatch ?: 0
        val total = data?.total ?: 0

        binding.watching.text = getString(R.string.format_statistic, getString(R.string.watching), watching.formatWithThousandsSeparator())
        binding.completed.text = getString(R.string.format_statistic, getString(R.string.completed), completed.formatWithThousandsSeparator())
        binding.onHold.text = getString(R.string.format_statistic, getString(R.string.on_hold), onHold.formatWithThousandsSeparator())
        binding.dropped.text = getString(R.string.format_statistic, getString(R.string.dropped), dropped.formatWithThousandsSeparator())
        binding.planToWatch.text = getString(R.string.format_statistic, getString(R.string.plan_to_watch), planToWatch.formatWithThousandsSeparator())

        binding.pieChart.apply {
            // Chart configuration
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.TRANSPARENT) // Set the holeColor to transparent
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(255)

            holeRadius = 72f
            transparentCircleRadius = 72f

            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            animateY(1400, Easing.EaseInOutQuad)
            legend.isEnabled = false
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)

            // Data
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(getPercentage(watching, total)))
            entries.add(PieEntry(getPercentage(completed, total)))
            entries.add(PieEntry(getPercentage(onHold, total)))
            entries.add(PieEntry(getPercentage(dropped, total)))
            entries.add(PieEntry(getPercentage(planToWatch, total)))

            val dataSet = PieDataSet(entries, "Anime Statistics")
            dataSet.setDrawIcons(false)

            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            // Hide the values (percentages)
            dataSet.setDrawValues(false)

            // Colors
            val colors = listOf(
                R.color.green,
                R.color.blue,
                R.color.yellow,
                R.color.red,
                R.color.grey_text
            ).map { ContextCompat.getColor(this@StatisticActivity, it) }
            dataSet.colors = colors

            val data = PieData(dataSet)
            binding.pieChart.data = data

            highlightValues(null)

            invalidate()
        }
    }

    private fun getPercentage(key: Int, total: Int): Float {
        return (key.toFloat() / total.toFloat()) * 100
    }

    companion object {
        const val STATISTIC_ANIME_ID = "STATISTIC_ANIME_ID"
        const val STATISTIC_ANIME_TITLE = "STATISTIC_ANIME_TITLE"
    }
}