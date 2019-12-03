package ru.skillbranch.gameofthrones.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.main.MainFragment

class SplashFragment: Fragment() {

    //companion object {
    //    /**
    //     * Returns a new instance of this fragment
    //     */
    //    @JvmStatic
    //    fun newInstance(): SplashFragment {
    //        return SplashFragment()
    //    }
    //}
//
    //private lateinit var splashViewModel: SplashViewModel
//
    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
    //}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        //splashViewModel.loaded.observe(this, Observer<Boolean> {
        //    fragmentManager!!.beginTransaction().replace(R.id.root_content, MainFragment()).commit()
        //})
        return root
    }

    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    //    super.onViewCreated(view, savedInstanceState)
    //    //TODO test
    //    setDataLoadedPostDelayed()
    //}
//
    //fun setDataLoadedPostDelayed(){
    //    Thread {
    //        sleep(3000)
    //        splashViewModel.setDataLoaded()
    //    }.start()
    //}
}