package ru.skillbranch.gameofthrones.ui.screens

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.LoadResult
import ru.skillbranch.gameofthrones.viewmodels.SplashViewModel


class SplashActivity : AppCompatActivity() {

    companion object {
        const val DELAY = 5000L
    }

    private val colorAnim = ObjectAnimator.ofFloat(0f, 0.7f)
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_main)
        initViewModel()
        startAnimateImageView()
        //getData()
        //checkDataIsReady()
        return
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.syncDataIfNeed().observe(this, Observer {
            when (it) {
                is LoadResult.Loading -> {

                }
                is LoadResult.Success -> {
                    //TODO check 5 seconds
                    val intent = Intent(this, CharactersListScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                is LoadResult.Error -> {
                    stopAnimateImageView()
                    Snackbar.make(ll_splash, R.string.no_internet, Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        })
        viewModel.getData()
    }

    private fun stopAnimateImageView() = runOnUiThread { colorAnim.cancel() }


    //private fun checkDataIsReady() {
    //    val r = Runnable {
    //        if (isHousesReady && isCharactersReady) showCharactersListScreen()
    //        else checkDataIsReady()
    //    }
    //    Handler().postDelayed(r,
    //        DELAY
    //    )
    //}


    //private fun showCharactersListScreen() {
    //    val intent = Intent(this, CharactersListScreen::class.java)
    //    startActivity(intent)
    //    finish()
    //}

    private fun startAnimateImageView() {
        val red = Color.RED
        colorAnim.addUpdateListener { animation ->
            val mul = animation.animatedValue as Float
            val alphaRed = adjustAlpha(red, mul)
            iv_splash.setColorFilter(alphaRed, PorterDuff.Mode.OVERLAY)
            if (mul.toDouble() == 0.0) {
                iv_splash.colorFilter = null
            }
        }
        colorAnim.duration = 1000
        colorAnim.repeatMode = ValueAnimator.REVERSE
        colorAnim.repeatCount = -1
        colorAnim.start()
    }

    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

}
