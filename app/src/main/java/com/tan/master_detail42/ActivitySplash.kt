package com.tan.master_detail42

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_splash.*

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        animateLogo(textView)
    }

    private fun animateLogo(tv: TextView, fadeIn: Boolean = true) {
        val anim = AlphaAnimation(if (fadeIn) 0f else 1f, if (fadeIn) 1f else 0f)
        anim.interpolator = AccelerateInterpolator()
        anim.duration = 1000L

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                if (fadeIn) {
                    Handler().postDelayed({ animateLogo(tv, false) }, 2000L)
                } else {
                    tv.visibility = View.GONE
                    startActivity(Intent(this@ActivitySplash, ActivityMaster::class.java))
                    finish()
                }
            }
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })

        tv.startAnimation(anim)
    }
}