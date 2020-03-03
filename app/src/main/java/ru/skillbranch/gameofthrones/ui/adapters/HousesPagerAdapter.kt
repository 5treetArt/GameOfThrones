package ru.skillbranch.gameofthrones.ui.adapters

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.ui.screens.HouseFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class HousesPagerAdapter(fm: FragmentManager, private val names: List<String> ) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = names.map { HouseFragment.newInstance(it) }

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int) = names[position]

    override fun getCount() = fragmentList.size
}