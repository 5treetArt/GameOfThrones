package ru.skillbranch.gameofthrones.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.skillbranch.gameofthrones.R

class MainFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(this.context!!, this.fragmentManager!!)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        return root
    }

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
