package com.example.stretchyourbody

import android.animation.Animator
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import android.content.Intent

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 70f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    private var radius = 0f
    private var currentText = ""

    private lateinit var animator: ValueAnimator

    // Tekst do wyświetlenia
    private val text = "Stretch YOUR Body"

    init {
        setupAnimator()
    }

    private fun setupAnimator() {
        animator = ValueAnimator.ofInt(0, text.length)
        animator.duration = 2000L
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            currentText = text.substring(0, animatedValue)
            invalidate()
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                Handler().postDelayed({
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }, 1000L) // Opóźnienie 1 sekundy przed uruchomieniem LoginActivity
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val ringRadius = radius - (circlePaint.strokeWidth / 2f)

        canvas.drawCircle(centerX, centerY, ringRadius, circlePaint)

        val textHeightOffset = (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(currentText, centerX, centerY - textHeightOffset, textPaint)
    }

    fun setRadius(newRadius: Float) {
        radius = newRadius
        invalidate()
    }

    fun startTextAnimation() {
        animator.start()
    }

    fun cancelTextAnimation() {
        animator.cancel()
    }
}
