package com.example.muhendisliktasarimi.domain.model

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class TextDrawable(private val text: String) : Drawable() {
    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 40f // Metin boyutu
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
    }

    override fun draw(canvas: Canvas) {
        val lines = text.split(" ") // Her kelime için satır oluştur
        val xOffset = 5 * Resources.getSystem().displayMetrics.density // Sağa kaydırma
        var yOffset = -30 * Resources.getSystem().displayMetrics.density // Yukarı kaydırma

        for (line in lines) {
            canvas.drawText(line, xOffset, bounds.height().toFloat() + yOffset, paint)
            yOffset += paint.textSize + 8 // Bir sonraki satır için yüksekliği artır
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
        paint.colorFilter = colorFilter
    }
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

}