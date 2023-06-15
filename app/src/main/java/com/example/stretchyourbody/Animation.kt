package com.example.stretchyourbody

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Animation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        animateCircle()
    }

    private fun animateCircle() {
        val startRadius = 0f
        val endRadius = 400f
        val duration = 2000L

        val circleView: CircleView = findViewById(com.example.stretchyourbody.R.id.circleView)


        val animator = ValueAnimator.ofFloat(startRadius, endRadius)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            circleView.setRadius(animatedValue)
        }
        animator.start()

        circleView.setRadius(200f) // Ustawienie promienia koła
        circleView.startTextAnimation() // Uruchomienie animacji tekstu




    }
}
