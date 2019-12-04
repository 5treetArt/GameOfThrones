package ru.skillbranch.gameofthrones.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.Utils.Utils
import ru.skillbranch.gameofthrones.ui.main.MainFragment
import java.lang.Thread.sleep
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SplashFragment: Fragment() {

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    private lateinit var splashViewModel: SplashViewModel

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private val countDownTimer = Thread {
        lock.withLock {
            sleep(2500)
            //TODO test
            splashViewModel.setDataLoaded()
            sleep(2500)
            condition.signalAll()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        (root.findViewById(R.id.iv_splash) as ImageView).setImageBitmap(Utils.scaleBitmapToMaxSize(resources, R.drawable.splash, activity!!.windowManager))
        countDownTimer.start()
        splashViewModel.loaded.observe(this, Observer<Boolean> {
            if (countDownTimer.isAlive) lock.withLock { condition.await() }
            fragmentManager!!.beginTransaction().replace(R.id.root_content, MainFragment()).commit()
        })
        return root
    }
}