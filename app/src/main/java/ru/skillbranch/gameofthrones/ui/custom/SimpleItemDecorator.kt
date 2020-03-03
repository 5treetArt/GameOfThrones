package ru.skillbranch.gameofthrones.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R

class SimpleItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable = context.resources.getDrawable(R.drawable.divider, context.theme)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val margin = parent.resources.getDimensionPixelSize(R.dimen.divider_margin)
        val right = parent.width - parent.paddingRight

        parent.children.forEach {
            val params = it.layoutParams as RecyclerView.LayoutParams
            val left =  parent.paddingLeft + margin
            val top = it.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

}