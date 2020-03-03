package ru.skillbranch.gameofthrones.ui.screens

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_characters_list_screen.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.viewmodels.MainViewModel
import ru.skillbranch.gameofthrones.ui.adapters.HousesPagerAdapter


class HousesActivity : AppCompatActivity() {
    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    private lateinit var houseViewModel: MainViewModel
    private lateinit var houseNames: List<String>
    private lateinit var colors: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list_screen)
        initToolbar()
        initViewModel()
        colors = applicationContext.run {
            arrayOf(
                getColor(R.color.stark_primary),
                getColor(R.color.lannister_primary),
                getColor(R.color.targaryen_primary),
                getColor(R.color.baratheon_primary),
                getColor(R.color.greyjoy_primary),
                getColor(R.color.martel_primary),
                getColor(R.color.tyrell_primary)
            )
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_character_list)
    }

    private fun initViewModel() {
        houseViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        houseViewModel.getHouses().observe(this, Observer { updateHouses(it) })
        houseViewModel.updateHouses()
    }

    private fun updateHouses(houses: List<House>) {
        houseNames = filterHouses(houses.map { it.name })
        val housesPagerAdapter = HousesPagerAdapter(supportFragmentManager, houseNames)

        view_pager.adapter = housesPagerAdapter
        tabs.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val color = getHeaderColor(position, positionOffset)
                app_bar.setBackgroundColor(color)
                tabs.setBackgroundColor(color)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {}
        })
        view_pager.currentItem = 0
    }

    private fun filterHouses(houses: List<String>): List<String> =
        AppConfig.NEED_HOUSES.mapNotNull {
            house -> houses.find { house.contains(it) }
        }

    private fun getHeaderColor(position: Int, positionOffset: Float): Int {
        val startColor: Int = colors[position]
        val endColor: Int = if (position < colors.size - 1) colors[position + 1] else colors[0]
        return argbEvaluator.evaluate(positionOffset, startColor, endColor) as Int
    }
}
