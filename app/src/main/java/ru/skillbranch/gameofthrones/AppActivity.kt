package ru.skillbranch.gameofthrones

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ru.skillbranch.gameofthrones.ui.splash.SplashFragment

class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        supportFragmentManager.beginTransaction().add(R.id.root_container, SplashFragment()).commit()
    }
}