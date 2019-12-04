package ru.skillbranch.gameofthrones.Utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES10
import android.util.DisplayMetrics
import android.view.WindowManager
import javax.microedition.khronos.opengles.GL10
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


object Utils {

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun decodeBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            inJustDecodeBounds = false
            BitmapFactory.decodeResource(res, resId, this)
        }
    }

    fun scaleBitmapToMaxSize(
        res: Resources,
        resId: Int,
        windowManager: WindowManager
    ): Bitmap =
        getMaxSupportScaledSize(res, resId, windowManager)
            .run { scaleBitmap(res, resId, first, second) }

    private fun getMaxSupportScaledSize(
        res: Resources,
        resId: Int,
        windowManager: WindowManager
    ): Pair<Int, Int> {
        val (screenWidth, screenHeight) = DisplayMetrics()
                .apply { windowManager.defaultDisplay.getMetrics(this) }
                .let { it.widthPixels to it.heightPixels }

        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)
            val widthScale = screenWidth / outWidth.toFloat()
            val heightScale = screenHeight / outHeight.toFloat()
            val minScale = min(widthScale, heightScale)
            return@run if (minScale >= 1f) outWidth to outHeight
            else (outWidth * minScale).roundToInt() to (outHeight * minScale).roundToInt()
        }
    }

    private fun scaleBitmap(res: Resources,
              resId: Int,
              reqWidth: Int,
              reqHeight: Int
    ) = decodeBitmapFromResource(res, resId, reqWidth, reqHeight)
        .run { Bitmap.createScaledBitmap(this, reqWidth, reqHeight, true) }

}